package com.tomo.service.category.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tomo.feign.CoinGeckoClient;
import com.tomo.model.ChainCoinGeckoEnum;
import com.tomo.model.ChainEnum;
import com.tomo.model.ChainUtil;
import com.tomo.model.CoinGeckoEnum;
import com.tomo.model.IntervalEnum;
import com.tomo.model.dto.TokenCategoryCoinGeckoDTO;
import com.tomo.model.dto.TokenInfoDTO;
import com.tomo.model.dto.TokenOhlcvDTO;
import com.tomo.model.dto.TokenPriceDTO;
import com.tomo.model.dto.TokenRiskGoplusDTO;
import com.tomo.model.req.OnchainTokenReq;
import com.tomo.model.req.PlatformTokenReq;
import com.tomo.model.resp.CoinInfoResp;
import com.tomo.model.resp.DexTokenResp;
import com.tomo.model.resp.TokenPriceResp;
import com.tomo.service.TokenInfoCacheService;
import com.tomo.service.category.CoinGeckoService;
import com.tomo.service.category.TokenCategoryDataService;
import com.tomo.service.category.TokenInfoService;
import com.tomo.service.price.KLineService;
import com.tomo.service.price.TokenPriceDataService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CoinGeckoServiceImpl implements CoinGeckoService {
    @Autowired
    TokenCategoryDataService tokenCategoryDataService;
    @Autowired
    TokenPriceDataService tokenPriceDataService;
    @Autowired
    TokenInfoService tokenInfoService;
    @Autowired
    CoinGeckoClient coinGeckoClient;
    @Autowired
    KLineService kLineService;
    @Autowired
    TokenInfoCacheService tokenInfoCacheService;


    @Override
    public TokenInfoDTO queryOneByOnchain(OnchainTokenReq tokenReq, boolean include) {
        LambdaQueryWrapper<TokenInfoDTO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TokenInfoDTO::getAddress, tokenReq.getAddress());
        queryWrapper.eq(TokenInfoDTO::getChainId, tokenReq.getChainId());
        List<TokenInfoDTO> list = tokenInfoService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            Map<String, TokenInfoDTO> infoDTOMap = singleOnchainTokenInfoAndPrice(List.of(tokenReq), include);
            if (infoDTOMap.containsKey(tokenReq.getAddress())) {
                return infoDTOMap.get(tokenReq.getAddress());
            }
        }
        return list.get(0);
    }

    @Override
    public Map<String, TokenInfoDTO> batchOnchainCoinInfoAndPrice(List<OnchainTokenReq> tokenList, boolean include) {
        Map<String, TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        if (tokenList.size() > 300) {
            return resultMap;
        }
        if (CollectionUtils.isEmpty(tokenList)) {
            return resultMap;
        }

        Map<Long, List<OnchainTokenReq>> chainTokenMap =
                tokenList.stream()
                        .filter(e -> StringUtils.hasText(e.getAddress()) && Objects.nonNull(e.getChainId()))
                        .collect(Collectors.groupingBy(OnchainTokenReq::getChainId));
        List<OnchainTokenReq> nativeTokenMap =
                tokenList.stream()
                        .filter(e -> !StringUtils.hasText(e.getAddress()) && Objects.nonNull(e.getChainId()))
                        .toList();

        chainTokenMap.forEach((chainId, tokens) -> {
            // 分隔成一组组
            List<List<OnchainTokenReq>> partitions = getPartitions(tokens, 30);
            // 遍历每组
            for (List<OnchainTokenReq> partition : partitions) {
                Map<String, TokenInfoDTO> map = singleOnchainTokenInfoAndPrice(partition, include);
                resultMap.putAll(map);
            }
        });

        List<PlatformTokenReq> nativeTokenList = nativeTokenMap
                .stream()
                .map(e -> {
                    PlatformTokenReq tokenReq = new PlatformTokenReq();
                    tokenReq.setCoingeckoCoinId(ChainUtil.getChainAndCoinGeckoMap().get(e.getChainId()).getCoinGeckoEnum().getCoinId());
                    return tokenReq;
                }).toList();
        Map<String, TokenInfoDTO> nativeTokenResultMap = batchPlatformCoinInfoAndPrice(nativeTokenList);
        resultMap.putAll(nativeTokenResultMap);

        return resultMap;
    }

    @Override
    public Map<String, TokenInfoDTO> singleOnchainTokenInfoAndPrice(List<OnchainTokenReq> partition,
                                                                    boolean include) {
        Map<String, TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        try {
            if (CollectionUtils.isEmpty(partition)) {
                return new HashMap<>();
            }
            Long chainId = partition.get(0).getChainId();
            ChainCoinGeckoEnum chainInfoEnum = ChainUtil.getChainAndCoinGeckoMap().get(chainId);
            if (Objects.isNull(chainInfoEnum)) {
                return new HashMap<>();
            }
            List<String> addresses = partition.stream().map(OnchainTokenReq::getAddress).filter(StringUtils::hasLength).toList();
            DexTokenResp dexTokenResp = coinGeckoClient.batchGetTokenInfo(chainInfoEnum.getCoinGeckoEnum().getNetworkId(), listToString(addresses));
            partition.forEach((oldTokenInfo) -> {
                Optional<DexTokenResp.DexData> dexDataOpt = dexTokenResp.getData().stream().filter((data -> data.getAttributes().getAddress().equalsIgnoreCase(oldTokenInfo.getAddress()))).findFirst();
                if (dexDataOpt.isEmpty()) {
                    return;
                }
                DexTokenResp.DexData dexData = dexDataOpt.get();
                DexTokenResp.DexData.Attributes attributes = dexData.getAttributes();
                String patternStr = getPatternStr(dexData.getId(), 0);
                TokenCategoryCoinGeckoDTO tokenCategoryCoinGeckoDTO = setOnchainTokenInfo(attributes);
                tokenCategoryCoinGeckoDTO.setChainId(chainId);
                TokenPriceDTO tokenPriceDTO = setOnchainTokenPrice(oldTokenInfo.getChainId(), attributes);
                tokenCategoryCoinGeckoDTO.setCoingeckoChainId(patternStr);
                tokenPriceDTO.setCoingeckoChainId(patternStr);
                if (include) {
                    setPoolInfo(dexTokenResp, dexData, tokenPriceDTO);
                }
                TokenInfoDTO tokenInfoDTO = convertTokenInfo(tokenCategoryCoinGeckoDTO, tokenPriceDTO, new TokenRiskGoplusDTO());
                tokenCategoryDataService.insertOrUpdate(tokenCategoryCoinGeckoDTO);
                tokenPriceDataService.insertOrUpdate(tokenPriceDTO);
                tokenInfoService.insertOrUpdate(tokenInfoDTO);
                resultMap.put(ChainUtil.getCommonKey(tokenInfoDTO.getChainId(), tokenInfoDTO.getAddress()), tokenInfoDTO);
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return resultMap;
    }

    private TokenInfoDTO convertTokenInfo(TokenCategoryCoinGeckoDTO tokenCategoryCoinGeckoDTO,
                                          TokenPriceDTO tokenPriceDTO,
                                          TokenRiskGoplusDTO tokenRiskGoplusDTO) {
        TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
        tokenInfoDTO.setChainId(tokenCategoryCoinGeckoDTO.getChainId());
        tokenInfoDTO.setAddress(tokenCategoryCoinGeckoDTO.getAddress());
        tokenInfoDTO.setCoingeckoCoinId(tokenCategoryCoinGeckoDTO.getCoingeckoCoinId());
        tokenInfoDTO.setCoingeckoChainId(tokenCategoryCoinGeckoDTO.getCoingeckoChainId());
        tokenInfoDTO.setIsNative(tokenCategoryCoinGeckoDTO.getIsNative());
        tokenInfoDTO.setName(tokenCategoryCoinGeckoDTO.getName());
        tokenInfoDTO.setDisplayName(tokenCategoryCoinGeckoDTO.getDisplayName());
        tokenInfoDTO.setSymbol(tokenCategoryCoinGeckoDTO.getSymbol());
        tokenInfoDTO.setImageUrl(tokenCategoryCoinGeckoDTO.getImageUrl());
        tokenInfoDTO.setDecimals(tokenCategoryCoinGeckoDTO.getDecimals());
        tokenInfoDTO.setWebsiteUrl(tokenCategoryCoinGeckoDTO.getWebsiteUrl());
        tokenInfoDTO.setTwitterUrl(tokenCategoryCoinGeckoDTO.getTwitterUrl());
        tokenInfoDTO.setTelegramUrl(tokenCategoryCoinGeckoDTO.getTelegramUrl());
        tokenInfoDTO.setPoolAddress(tokenPriceDTO.getPoolAddress());
        tokenInfoDTO.setIsPoolBaseToken(tokenPriceDTO.getIsPoolBaseToken());
        tokenInfoDTO.setRealPrice(tokenPriceDTO.getRealPrice());
        tokenInfoDTO.setLiquidityUsd(tokenPriceDTO.getLiquidityUsd());
        tokenInfoDTO.setVolume24h(tokenPriceDTO.getVolume24h());
        tokenInfoDTO.setChange24h(tokenPriceDTO.getChange24h());
        tokenInfoDTO.setMarketCap(tokenPriceDTO.getMarketCap());
        tokenInfoDTO.setFdvUsd(tokenPriceDTO.getFdvUsd());
        tokenInfoDTO.setTotalSupply(tokenPriceDTO.getTotalSupply());
        tokenInfoDTO.setRiskLevel(tokenRiskGoplusDTO.getRiskLevel());
        return tokenInfoDTO;
    }

    private static String getPatternStr(String data, int index) {
        String regex = "^[^_]+_(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        if (matcher.matches()) {
            return matcher.group(index);
        }
        return null;
    }

    private void setPoolInfo(DexTokenResp dexTokenResp, DexTokenResp.DexData dexData, TokenPriceDTO tokenPriceDTO) {
        if (dexTokenResp.getIncluded() == null) {
            return;
        }
        List<DexTokenResp.DexData.Pool> included = dexTokenResp.getIncluded();
        List<DexTokenResp.DexData.TokenData> topTools = dexData.getRelationships().getTopPools().getData();
        if (!CollectionUtils.isEmpty(topTools)) {
            String topToolId = topTools.get(0).getId();
            tokenPriceDTO.setPoolAddress(getPatternStr(topToolId, 1));
            Optional<DexTokenResp.DexData.Pool> topToolOpt = included.stream().filter((t) -> t.getId().equals(topToolId)).findFirst();
            if (topToolOpt.isPresent()) {
                tokenPriceDTO.setIsPoolBaseToken(topToolOpt.get().getRelationships().getBaseToken().getData().getId().equals(dexData.getId()));
                if (topToolOpt.get().getAttributes().getReserveInUsd() != null) {
                    tokenPriceDTO.setLiquidityUsd(new BigDecimal(topToolOpt.get().getAttributes().getReserveInUsd()));
                }
            }
        }
    }


    @Override
    public Map<String, TokenInfoDTO> batchPlatformCoinInfoAndPrice(List<PlatformTokenReq> tokenList) {
        Map<String, TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        if (tokenList.size() > 300) {
            return resultMap;
        }
        if (CollectionUtils.isEmpty(tokenList)) {
            return resultMap;
        }
        for (PlatformTokenReq tokenReq : tokenList) {
            Map<String, TokenInfoDTO> map = singlePlatformTokenInfoAndPrice(tokenReq);
            resultMap.putAll(map);
        }
        return resultMap;
    }


    @Override
    public Map<String, TokenInfoDTO> singlePlatformTokenInfoAndPrice(PlatformTokenReq token) {
        Map<String, TokenInfoDTO> resultMap = new HashMap<>();
        try {// coingecko请求基本数据
            CoinInfoResp onlineTokenInfo = coinGeckoClient.getPlatformCoinInfo(token.getCoingeckoCoinId());
            Map<String, TokenPriceResp> platformTokenPriceMap = coinGeckoClient.getPlatformTokenPrice(token.getCoingeckoCoinId());
            TokenPriceResp onlineTokenPrice = platformTokenPriceMap.get(token.getCoingeckoCoinId());
            if (CollectionUtils.isEmpty(platformTokenPriceMap)) {
                return resultMap;
            }
            // 不同平台,循环遍历
            onlineTokenInfo.getPlatforms().forEach((assetPlatformId, address) -> {
                try {
                    TokenCategoryCoinGeckoDTO coinGeckoDTO = new TokenCategoryCoinGeckoDTO();
                    boolean isNative = !StringUtils.hasText(assetPlatformId) && !StringUtils.hasText(address);
                    if (isNative) {
                        coinGeckoDTO.setIsNative(true);
                        List<ChainCoinGeckoEnum> chainCoinGeckoEnums = ChainUtil.getNativeIdToEnum().getOrDefault(token.getCoingeckoCoinId(),new ArrayList<>());
                        for (ChainCoinGeckoEnum coinGeckoEnum : chainCoinGeckoEnums) {
                            setPlatformInfo(coinGeckoEnum, coinGeckoDTO, onlineTokenInfo, onlineTokenPrice, resultMap,false);
                        }
                    }else {
                        ChainCoinGeckoEnum coinGeckoEnum = ChainUtil.getCoinGeckoChainInfoMap().getOrDefault(assetPlatformId, null);
                        setPlatformInfo(coinGeckoEnum, coinGeckoDTO, onlineTokenInfo, onlineTokenPrice, resultMap,false);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultMap;
    }

    private void setPlatformInfo(ChainCoinGeckoEnum coinGeckoEnum,
                                 TokenCategoryCoinGeckoDTO coinGeckoDTO,
                                 CoinInfoResp onlineTokenInfo,
                                 TokenPriceResp onlineTokenPrice,
                                 Map<String, TokenInfoDTO> resultMap,
                                 boolean isNative) {
        coinGeckoDTO.setIsNative(isNative);
        if (coinGeckoEnum != null) {
            coinGeckoDTO.setChainId(coinGeckoEnum.getChainId());
            TokenPriceDTO tokenPriceDTO = new TokenPriceDTO();
            CoinInfoResp.DetailPlatform detailPlatform = onlineTokenInfo.getDetailPlatforms().getOrDefault(coinGeckoEnum.getCoinGeckoEnum().getPlatformChainId(),null);
            setTokenInfoAndPrice(coinGeckoEnum.getChainId(), detailPlatform, onlineTokenInfo, onlineTokenPrice, coinGeckoDTO, tokenPriceDTO);
            TokenInfoDTO tokenInfoDTO = convertTokenInfo(coinGeckoDTO, tokenPriceDTO, new TokenRiskGoplusDTO());
            tokenCategoryDataService.insertOrUpdate(coinGeckoDTO);
            tokenPriceDataService.insertOrUpdate(tokenPriceDTO);
            tokenInfoService.insertOrUpdate(tokenInfoDTO);
            resultMap.put(ChainUtil.getCommonKey(tokenInfoDTO.getChainId(), tokenInfoDTO.getAddress()), tokenInfoDTO);
        }
    }

    @Override
    public Map<String, TokenInfoDTO> updateNativeOrPlatformPrice(List<PlatformTokenReq> tokens) {
        Map<String, TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        List<String> list = tokens.stream().map(PlatformTokenReq::getCoingeckoCoinId).toList();
        Map<String, TokenPriceResp> platformTokenPriceMap = coinGeckoClient.getPlatformTokenPrice(listToString(list));
        tokens.forEach(token -> {
            TokenPriceResp onlineTokenPrice = platformTokenPriceMap.get(token.getCoingeckoCoinId());
            if (onlineTokenPrice == null) {
                return;
            }
            CoinGeckoEnum coinGeckoEnum = CoinGeckoEnum.getCoinGeckoEnumNativeId(token.getCoingeckoCoinId());
            if (coinGeckoEnum == null) {
                return;
            }
            ChainEnum chainEnum = ChainEnum.getChainEnum(coinGeckoEnum.name());
            TokenPriceDTO tokenPriceDTO = new TokenPriceDTO();
            tokenPriceDTO.setChainId(chainEnum.getChainId());
            tokenPriceDTO.setRealPrice(BigDecimal.valueOf(onlineTokenPrice.getRealPrice()));
            tokenPriceDTO.setChange24h(BigDecimal.valueOf(onlineTokenPrice.getChange24h()));
            tokenPriceDTO.setMarketCap(BigDecimal.valueOf(onlineTokenPrice.getMarketCap()));
            tokenPriceDTO.setVolume24h(BigDecimal.valueOf(onlineTokenPrice.getVolume24h()));
            TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
            tokenInfoDTO.setRealPrice(BigDecimal.valueOf(onlineTokenPrice.getRealPrice()));
            tokenInfoDTO.setChange24h(BigDecimal.valueOf(onlineTokenPrice.getChange24h()));
            tokenInfoDTO.setMarketCap(BigDecimal.valueOf(onlineTokenPrice.getMarketCap()));
            tokenInfoDTO.setVolume24h(BigDecimal.valueOf(onlineTokenPrice.getVolume24h()));

            tokenPriceDataService.insertOrUpdate(tokenPriceDTO);
            tokenInfoService.insertOrUpdate(tokenInfoDTO);
            resultMap.put(token.getCoingeckoCoinId(), tokenInfoDTO);
        });
        return resultMap;
    }

    @Override
    public List<TokenOhlcvDTO> getPlatformTokenOhlcv(Long chainId, String address, IntervalEnum interval) {
        return kLineService.getTokenOhlcv(chainId, address, interval);
    }


    private static void setTokenInfoAndPrice(Long chainId,
                                             @Nullable CoinInfoResp.DetailPlatform detailPlatform,
                                             CoinInfoResp oldTokenCategory,
                                             TokenPriceResp oldTokenPrice,
                                             TokenCategoryCoinGeckoDTO newTokenCategoryCoinGeckoDTO,
                                             TokenPriceDTO newTokenPrice) {
        // token
        setPlatformTokenInfo(oldTokenCategory, detailPlatform, newTokenCategoryCoinGeckoDTO);
        newTokenCategoryCoinGeckoDTO.setChainId(chainId);
//        newTokenCategoryCoinGeckoDTO.setCoingeckoChainId(ChainUtil.getChainAndCoinGeckoMap().get(chainId).getCoinGeckoEnum().getPlatformChainId());
        // price
        setPlatformTokenPrice(newTokenPrice, oldTokenPrice, oldTokenCategory);
        newTokenPrice.setChainId(chainId);
        newTokenPrice.setAddress(oldTokenCategory.getContractAddress());
    }


    private static void setPlatformTokenInfo(CoinInfoResp platformCoinInfo,
                                             CoinInfoResp.DetailPlatform detailPlatform,
                                             TokenCategoryCoinGeckoDTO newTokenCategoryCoinGeckoDTO) {
        if (detailPlatform != null) {
            newTokenCategoryCoinGeckoDTO.setAddress(detailPlatform.getContractAddress());
            newTokenCategoryCoinGeckoDTO.setDecimals(detailPlatform.getDecimalPlace());
        }
        if (platformCoinInfo != null) {
            newTokenCategoryCoinGeckoDTO.setCoingeckoCoinId(platformCoinInfo.getId());
            newTokenCategoryCoinGeckoDTO.setName(platformCoinInfo.getName());
            newTokenCategoryCoinGeckoDTO.setSymbol(platformCoinInfo.getSymbol());
            newTokenCategoryCoinGeckoDTO.setImageUrl(platformCoinInfo.getImage().getSmall());
            if (platformCoinInfo.getLinks() != null) {
                if (!CollectionUtils.isEmpty(platformCoinInfo.getLinks().getHomepage()) && StringUtils.hasLength(platformCoinInfo.getLinks().getHomepage().get(0))) {
                    newTokenCategoryCoinGeckoDTO.setWebsiteUrl(platformCoinInfo.getLinks().getHomepage().get(0));
                }
                if (StringUtils.hasLength(platformCoinInfo.getLinks().getTwitterScreenName())) {
                    newTokenCategoryCoinGeckoDTO.setTwitterUrl(String.format("https://x.com/%s", platformCoinInfo.getLinks().getTwitterScreenName()));
                }
                if (StringUtils.hasLength(platformCoinInfo.getLinks().getTelegramChannelIdentifier())) {
                    newTokenCategoryCoinGeckoDTO.setTelegramUrl(String.format("https://t.me/%s", platformCoinInfo.getLinks().getTelegramChannelIdentifier()));
                }
            }
        }
    }

    private static void setPlatformTokenPrice(TokenPriceDTO newTokenPrice,
                                              TokenPriceResp tokenPriceResp,
                                              CoinInfoResp platformCoinInfo) {
        newTokenPrice.setRealPrice(BigDecimal.valueOf(tokenPriceResp.getRealPrice()));
        newTokenPrice.setVolume24h(BigDecimal.valueOf(tokenPriceResp.getVolume24h()));
        newTokenPrice.setChange24h(BigDecimal.valueOf(tokenPriceResp.getChange24h()));
        newTokenPrice.setMarketCap(BigDecimal.valueOf(tokenPriceResp.getMarketCap()));
        if (platformCoinInfo.getMarketData() != null && platformCoinInfo.getMarketData().getFullyDilutedValuation() != null) {
            newTokenPrice.setFdvUsd(platformCoinInfo.getMarketData().getFullyDilutedValuation().getUsd());
        }
        if (platformCoinInfo.getMarketData() != null && platformCoinInfo.getMarketData().getTotalSupply() != null) {
            newTokenPrice.setTotalSupply(platformCoinInfo.getMarketData().getTotalSupply().setScale(0, RoundingMode.FLOOR));
        }
    }


    private static TokenCategoryCoinGeckoDTO setOnchainTokenInfo(DexTokenResp.DexData.Attributes attributes) {
        TokenCategoryCoinGeckoDTO newTokenInfo = new TokenCategoryCoinGeckoDTO();
        newTokenInfo.setIsNative(false);
        newTokenInfo.setAddress(attributes.getAddress());
        newTokenInfo.setCoingeckoCoinId(attributes.getCoingeckoCoinId());
        newTokenInfo.setDecimals(attributes.getDecimals());
        newTokenInfo.setName(attributes.getName());
        newTokenInfo.setSymbol(attributes.getSymbol());
        String imageUrl = attributes.getImageUrl();
        if (!"missing.png".equals(imageUrl)) {
            newTokenInfo.setImageUrl(imageUrl);
        }
        return newTokenInfo;
    }

    private static TokenPriceDTO setOnchainTokenPrice(Long chainId,
                                                      DexTokenResp.DexData.Attributes attributes) {
        TokenPriceDTO tokenPriceDTO = new TokenPriceDTO();
        tokenPriceDTO.setChainId(chainId);
        tokenPriceDTO.setAddress(attributes.getAddress());
        if (attributes.getVolumeUsd() != null && attributes.getVolumeUsd().getH24() != null) {
            tokenPriceDTO.setVolume24h(attributes.getVolumeUsd().getH24());
        }
        if (attributes.getPriceUsd() != null) {
            tokenPriceDTO.setRealPrice(attributes.getPriceUsd());
        }
        if (attributes.getMarketCapUsd() != null) {
            BigDecimal marketCapUsd = attributes.getMarketCapUsd();
            if (attributes.getFdvUsd() != null && attributes.getMarketCapUsd().compareTo(attributes.getFdvUsd()) > 0) {
                marketCapUsd = attributes.getFdvUsd();
            }
            tokenPriceDTO.setMarketCap(marketCapUsd);
        }
        if (attributes.getFdvUsd() != null) {
            tokenPriceDTO.setFdvUsd(attributes.getFdvUsd());
        }
        if (attributes.getPriceChangePercentage() != null) {
            tokenPriceDTO.setChange24h(attributes.getPriceChangePercentage().getH24());
        }
        if (attributes.getTotalSupply() != null && attributes.getDecimals() != null) {
            tokenPriceDTO.setTotalSupply(attributes.getTotalSupply().movePointLeft(attributes.getDecimals()).setScale(0, RoundingMode.FLOOR));
        }
        return tokenPriceDTO;
    }

    public static String listToString(List<String> list) {
        return String.join(",", list);
    }

    public static <T> List<List<T>> getPartitions(List<T> tokens, int partitionNum) {
        List<List<T>> partitions = new LinkedList<>();
        for (int i = 0; i < tokens.size(); i += partitionNum) {
            partitions.add(tokens.subList(i, Math.min(i + partitionNum, tokens.size())));
        }
        return partitions;
    }


//    public List<TokenInfoDTO> getTokenInfoFromCache(List<TokenBase> tokenBases) {
//        List<TokenInfoDTO> tokenInfoDTOS = tokenInfoCacheService.hGetAll(tokenBases);
//        Set<String> allToken = tokenBases.stream().map(TokenBase::getId).collect(Collectors.toSet());
//        Set<String> allCacheToken = tokenInfoDTOS.stream().map(TokenInfoDTO::getId).collect(Collectors.toSet());
//        allToken.removeAll(allCacheToken);
//    }
//
//    public List<TokenInfoDTO> getTokenInfoFromDB(List<TokenBase> tokenBases) {
//        List<TokenInfoDTO> tokenInfoDTOS = tokenInfoService.batchQuery(tokenBases);
//        Set<String> allToken = tokenBases.stream().map(TokenBase::getId).collect(Collectors.toSet());
//        Set<String> allCacheToken = tokenInfoDTOS.stream().map(TokenInfoDTO::getId).collect(Collectors.toSet());
//        allToken.removeAll(allCacheToken);
//    }
}
