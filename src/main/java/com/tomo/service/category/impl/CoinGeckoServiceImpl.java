package com.tomo.service.category.impl;

import com.tomo.feign.CoinGeckoClient;
import com.tomo.model.IntervalEnum;
import com.tomo.model.dto.TokenCategoryCoinGeckoDTO;
import com.tomo.model.dto.TokenInfoDTO;
import com.tomo.model.dto.TokenOhlcvDTO;
import com.tomo.model.dto.TokenPriceDTO;
import com.tomo.model.dto.TokenRiskGoplusDTO;
import com.tomo.model.req.TokenReq;
import com.tomo.model.resp.CoinInfoResp;
import com.tomo.model.resp.DexTokenResp;
import com.tomo.model.resp.TokenPriceResp;
import com.tomo.service.category.CoinGeckoService;
import com.tomo.service.category.TokenCategoryDataService;
import com.tomo.service.category.TokenInfoService;
import com.tomo.service.price.KLineService;
import com.tomo.service.price.TokenPriceDataService;
import com.tomo.model.ChainInfoEnum;
import com.tomo.model.ChainUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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



    @Override
    public Map<String, TokenInfoDTO> batchOnchainCoinInfoAndPrice(List<TokenReq> tokenList, boolean include) {
        Map<String,TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        if (tokenList.size() > 300){
            return resultMap;
        }
        if (CollectionUtils.isEmpty(tokenList)) {
            return resultMap;
        }
        Map<Long, List<TokenReq>> chainTokenMap =
                                        tokenList.stream()
                                                        .filter(e -> Objects.nonNull(e.getChainId()))
                                                        .collect(Collectors.groupingBy(TokenReq::getChainId));
        chainTokenMap.forEach((chainId, tokens) -> {
            // 分隔成一组组
            List<List<TokenReq>> partitions = getPartitions(tokens, 30);
            // 遍历每组
            List<Map<String, TokenInfoDTO>> list = partitions.stream().map((e -> singleOnchainTokenInfoAndPrice(e, include))).toList();
            for (Map<String, TokenInfoDTO> map : list) {
                resultMap.putAll(map);
            }
        });
        return resultMap;
    }

    @Override
    public Map<String, TokenInfoDTO> singleOnchainTokenInfoAndPrice(List<TokenReq> partition,
                                                                    boolean include) {
        Map<String,TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        try {
            if (CollectionUtils.isEmpty(partition)) {
                return new HashMap<>();
            }
            Long chainId = partition.get(0).getChainId();
            ChainInfoEnum chainInfoEnum = ChainUtil.getChainInfoMap().get(chainId);
            List<String> addresses = partition.stream().map(TokenReq::getAddress).filter(StringUtils::hasLength).toList();
            DexTokenResp dexTokenResp = coinGeckoClient.batchGetTokenInfo(chainInfoEnum.getCoingeckoOnchainName(), listToString(addresses));
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
        }catch (Exception e) {
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
    public Map<String, TokenInfoDTO> batchPlatformCoinInfoAndPrice(List<TokenCategoryCoinGeckoDTO> tokenList) {
        Map<String,TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        if (tokenList.size() > 300){
            return resultMap;
        }
        if (CollectionUtils.isEmpty(tokenList)) {
            return resultMap;
        }
        List<Map<String, TokenInfoDTO>> list = tokenList.stream().map(this::singlePlatformTokenInfoAndPrice).toList();
        for (Map<String, TokenInfoDTO> map : list) {
            resultMap.putAll(map);
        }
        return resultMap;
    }

    @Override
    public Map<String, TokenInfoDTO> singlePlatformTokenInfoAndPrice(TokenCategoryCoinGeckoDTO token) {
        Map<String, TokenInfoDTO> resultMap = new HashMap<>();
        try {// coingecko请求基本数据
            CoinInfoResp oldTokenCategory = coinGeckoClient.getPlatformCoinInfo(token.getCoingeckoCoinId());
            Map<String, TokenPriceResp> platformTokenPriceMap = coinGeckoClient.getPlatformTokenPrice(token.getCoingeckoCoinId());
            TokenPriceResp oldTokenPrice = platformTokenPriceMap.get(token.getCoingeckoCoinId());
            if (CollectionUtils.isEmpty(platformTokenPriceMap)) {
                return new HashMap<>();
            }
            // 不同平台,循环遍历
            oldTokenCategory.getPlatforms().forEach((assetPlatformId, address) -> {
                try {
                    TokenCategoryCoinGeckoDTO cloneToken = (TokenCategoryCoinGeckoDTO) token.clone();
                    boolean isNative = !StringUtils.hasText(assetPlatformId) && !StringUtils.hasText(address) && cloneToken.getIsNative() != null && cloneToken.getIsNative();
                    cloneToken.setIsNative(isNative);
                    if (isNative || ChainUtil.getCoinGeckoChainInfoMap().containsKey(assetPlatformId)) {
                        TokenCategoryCoinGeckoDTO tokenCategoryCoinGeckoDTO = (TokenCategoryCoinGeckoDTO) cloneToken.clone();
                        TokenPriceDTO tokenPriceDTO = new TokenPriceDTO();
                        CoinInfoResp.DetailPlatform detailPlatform = oldTokenCategory.getDetailPlatforms().get(assetPlatformId);
                        setTokenInfoAndPrice(assetPlatformId, detailPlatform, oldTokenCategory, oldTokenPrice, tokenCategoryCoinGeckoDTO, tokenPriceDTO);
                        TokenInfoDTO tokenInfoDTO = convertTokenInfo(tokenCategoryCoinGeckoDTO, tokenPriceDTO, new TokenRiskGoplusDTO());
                        tokenCategoryDataService.insertOrUpdate(tokenCategoryCoinGeckoDTO);
                        tokenPriceDataService.insertOrUpdate(tokenPriceDTO);
                        tokenInfoService.insertOrUpdate(tokenInfoDTO);
                        resultMap.put(ChainUtil.getCommonKey(tokenInfoDTO.getChainId(), tokenInfoDTO.getAddress()), tokenInfoDTO);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultMap;
    }


    @Override
    public List<TokenOhlcvDTO> getPlatformTokenOhlcv(Long chainId, String address, IntervalEnum interval) {
        return kLineService.getTokenOhlcv(chainId, address, interval);
    }


    private static void setTokenInfoAndPrice(String assetPlatformId,
                                             CoinInfoResp.DetailPlatform detailPlatform,
                                             CoinInfoResp oldTokenCategory,
                                             TokenPriceResp oldTokenPrice,
                                             TokenCategoryCoinGeckoDTO newTokenCategoryCoinGeckoDTO,
                                             TokenPriceDTO newTokenPrice) {
        ChainInfoEnum chainInfoEnum;
        // native token
        if (newTokenCategoryCoinGeckoDTO.getIsNative()) {
            chainInfoEnum = ChainUtil.getChainInfoMap().get(newTokenCategoryCoinGeckoDTO.getChainId());
        }
        // non native token
        else {
            chainInfoEnum = ChainUtil.getCoinGeckoChainInfoMap().get(assetPlatformId);
        }
        // chain value
        if (chainInfoEnum != null && StringUtils.hasText(chainInfoEnum.getCoingeckoChainName())) {
            // token
            setPlatformTokenInfo(oldTokenCategory, detailPlatform, newTokenCategoryCoinGeckoDTO);
            newTokenCategoryCoinGeckoDTO.setChainId(chainInfoEnum.getChainId());
            newTokenCategoryCoinGeckoDTO.setCoingeckoChainId(!StringUtils.hasText(assetPlatformId) ? chainInfoEnum.getCoingeckoChainName() : assetPlatformId);
            // price
            setPlatformTokenPrice(newTokenPrice, oldTokenPrice, oldTokenCategory);
            newTokenPrice.setChainId(chainInfoEnum.getChainId());
            newTokenPrice.setAddress(oldTokenCategory.getContractAddress());
        }
    }


    private static void setPlatformTokenInfo(CoinInfoResp platformCoinInfo,
                                             CoinInfoResp.DetailPlatform detailPlatform,
                                             TokenCategoryCoinGeckoDTO newTokenCategoryCoinGeckoDTO) {
        newTokenCategoryCoinGeckoDTO.setCoingeckoCoinId(platformCoinInfo.getId());
        if (detailPlatform != null) {
            newTokenCategoryCoinGeckoDTO.setAddress(detailPlatform.getContractAddress());
            newTokenCategoryCoinGeckoDTO.setDecimals(detailPlatform.getDecimalPlace());
        }
        newTokenCategoryCoinGeckoDTO.setName(platformCoinInfo.getName());
        newTokenCategoryCoinGeckoDTO.setSymbol(platformCoinInfo.getSymbol());
        newTokenCategoryCoinGeckoDTO.setImageUrl(platformCoinInfo.getImage().getSmall());
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
}
