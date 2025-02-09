package com.tomo.service.category.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tomo.feign.CoinGeckoClient;
import com.tomo.model.ChainCoinGeckoEnum;
import com.tomo.model.ChainEnum;
import com.tomo.model.ChainUtil;
import com.tomo.model.CoinGeckoEnum;
import com.tomo.model.IntervalEnum;
import com.tomo.model.TokenBase;
import com.tomo.model.dto.TokenCategoryCoinGeckoDTO;
import com.tomo.model.dto.TokenInfoDTO;
import com.tomo.model.dto.TokenOhlcvDTO;
import com.tomo.model.dto.TokenPriceDTO;
import com.tomo.model.dto.TokenRiskGoplusDTO;
import com.tomo.model.req.OnchainTokenReq;
import com.tomo.model.req.PlatformTokenReq;
import com.tomo.model.resp.*;
import com.tomo.service.TokenInfoCacheService;
import com.tomo.service.category.CoinGeckoService;
import com.tomo.service.category.TokenCategoryDataService;
import com.tomo.service.category.TokenInfoService;
import com.tomo.service.price.KLineService;
import com.tomo.service.price.TokenPriceDataService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
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

    /**
     * @param rawTokenList
     * @param include      是否查询池子信息
     * @return
     */
    @Override
    public Map<String, TokenInfoDTO> batchOnchainCoinInfoAndPrice(List<OnchainTokenReq> rawTokenList, boolean include) {
        Map<String, TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        if (rawTokenList.size() > 300) {
            return resultMap;
        }
        if (CollectionUtils.isEmpty(rawTokenList)) {
            return resultMap;
        }

        // 数据库查
        List<TokenBase> list = rawTokenList.stream().map(e -> new TokenBase(e.getAddress(), e.getChainId())).toList();
        Pair<Map<String, TokenInfoDTO>, List<TokenBase>> tokenInfoFromDB = getTokenInfoFromDB(list);
        // 已经存在了的结果
        Map<String, TokenInfoDTO> first = tokenInfoFromDB.getFirst();
        // 不存在的结果
        List<TokenBase> second = tokenInfoFromDB.getSecond();
        List<OnchainTokenReq> tokenList = second.stream().map(e -> new OnchainTokenReq(e.getChainId(), e.getAddress())).toList();

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
                    ChainCoinGeckoEnum chainCoinGeckoEnum = ChainUtil.getChainAndCoinGeckoMap().get(e.getChainId());
                    if (chainCoinGeckoEnum != null) {
                        tokenReq.setCoingeckoCoinId(chainCoinGeckoEnum.getCoinGeckoEnum().getCoinId());
                    } else {
                        log.error("CoinGeckoServiceImpl batchOnchainCoinInfoAndPrice chainId not exist:{}", e.getChainId());
                    }
                    return tokenReq;
                }).toList();
        Map<String, TokenInfoDTO> nativeTokenResultMap = batchPlatformCoinInfoAndPrice(nativeTokenList);
        resultMap.putAll(nativeTokenResultMap);


        if (!CollectionUtils.isEmpty(first)) {
            Map<String, TokenInfoDTO> existMap = updateOnchainPrice(first);
            resultMap.putAll(existMap);
        }
        return resultMap;
    }

    /**
     *
     * @param rawTokenList
     * @param include  是否查询池子信息
     * @return
     */
    @Override
    public Map<String, TokenInfoDTO> batchOnchainCoinInfoAndPriceV2(List<OnchainTokenReq> rawTokenList, boolean include) {
        Map<String, TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        if (rawTokenList.size() > 300) {
            return resultMap;
        }
        if (CollectionUtils.isEmpty(rawTokenList)) {
            return resultMap;
        }
        // 1. 查数据库，分成已入库未入库两类
        List<TokenBase> list = rawTokenList.stream().map(e -> new TokenBase(e.getAddress(), e.getChainId())).toList();
        Pair<Map<String, TokenInfoDTO>, List<TokenBase>> tokenInfoFromDB = getTokenInfoFromDB(list);
        Map<String, TokenInfoDTO> exist = tokenInfoFromDB.getFirst();
        List<TokenBase> notExist = tokenInfoFromDB.getSecond();
        //2. 处理未入库数据
        Map<String, TokenInfoDTO> stringTokenInfoDTOMap = handleNotExistToken(notExist, include);
        //3. 聚合返回
        resultMap.putAll(exist);
        resultMap.putAll(stringTokenInfoDTOMap);
        return resultMap;

    }

    private Map<String, TokenInfoDTO> handleNotExistToken( List<TokenBase> second, boolean include) {
        List<OnchainTokenReq> tokenList = second.stream().map(e -> new OnchainTokenReq(e.getChainId(), e.getAddress())).toList();
        //存在地址的数据
        Map<Long, List<OnchainTokenReq>> chainTokenMap = tokenList.stream()
                        .filter(e -> StringUtils.hasText(e.getAddress()) && Objects.nonNull(e.getChainId()))
                        .collect(Collectors.groupingBy(OnchainTokenReq::getChainId));
        //不存在地址的数据
        List<OnchainTokenReq> nativeTokenMap = tokenList.stream()
                        .filter(e -> !StringUtils.hasText(e.getAddress()) && Objects.nonNull(e.getChainId()))
                        .toList();
        //处理链token
        Map<String, TokenInfoDTO> stringTokenInfoDTOMap = handleChainToken(chainTokenMap, include);
        Map<String, TokenInfoDTO> resultMap = new HashMap<>(stringTokenInfoDTOMap);

        //处理链nativeToken
        Map<String, TokenInfoDTO> nativeTokenResultMap = handleNativeToken(nativeTokenMap);
        resultMap.putAll(nativeTokenResultMap);
        return resultMap;
    }

    private Map<String, TokenInfoDTO> handleNativeToken(List<OnchainTokenReq> nativeTokenMap) {
        List<PlatformTokenReq> nativeTokenList = nativeTokenMap
                .stream()
                .map(e -> {
                    PlatformTokenReq tokenReq = new PlatformTokenReq();
                    ChainCoinGeckoEnum chainCoinGeckoEnum = ChainUtil.getChainAndCoinGeckoMap().get(e.getChainId());
                    if(chainCoinGeckoEnum!=null){
                        tokenReq.setCoingeckoCoinId(chainCoinGeckoEnum.getCoinGeckoEnum().getCoinId());
                    }else{
                        log.error("CoinGeckoServiceImpl batchOnchainCoinInfoAndPrice chainId not exist:{}",e.getChainId());
                    }
                    return tokenReq;
                }).toList();
        return batchPlatformCoinInfoAndPrice(nativeTokenList);
    }

    private Map<String, TokenInfoDTO>  handleChainToken(Map<Long, List<OnchainTokenReq>> chainTokenMap, boolean include) {
        Map<String, TokenInfoDTO> resultMap=new HashMap<>();
        chainTokenMap.forEach((chainId, tokens) -> {
            // 分隔成30一组,三方接口限制
            List<List<OnchainTokenReq>> partitions = getPartitions(tokens, 30);
            // 遍历每组
            for (List<OnchainTokenReq> partition : partitions) {
                Map<String, TokenInfoDTO> map = singleOnchainTokenInfoAndPrice(partition, include);
                resultMap.putAll(map);
            }
        });
        return resultMap;
    }

    @Override
    public Map<String, TokenInfoDTO> singleOnchainTokenInfoAndPrice(List<OnchainTokenReq> partition,
                                                                    boolean include) {
        Map<String, TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        List<TokenCategoryCoinGeckoDTO> tokenCategoryUpdateList=new ArrayList<>();
        List<TokenPriceDTO> tokenPriceDTOUpdateList=new ArrayList<>();
        List<TokenInfoDTO> tokenInfoDTOUpdateList=new ArrayList<>();
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
//                tokenCategoryDataService.insertOrUpdate(tokenCategoryCoinGeckoDTO);
//                tokenPriceDataService.insertOrUpdate(tokenPriceDTO);
//                tokenInfoService.insertOrUpdate(tokenInfoDTO);
                tokenCategoryUpdateList.add(tokenCategoryCoinGeckoDTO);
                tokenPriceDTOUpdateList.add(tokenPriceDTO);
                tokenInfoDTOUpdateList.add(tokenInfoDTO);
                resultMap.put(ChainUtil.getCommonKey(tokenInfoDTO.getChainId(), tokenInfoDTO.getAddress()), tokenInfoDTO);
            });
            //批量更新 可以异步优化
            tokenCategoryDataService.batchInsertOrUpdate(tokenCategoryUpdateList);
            tokenPriceDataService.batchInsertOrUpdate(tokenPriceDTOUpdateList);
            tokenInfoService.batchInsertOrUpdate(tokenInfoDTOUpdateList);
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

    public Map<String, TokenPriceResp> batchPlatformPrice(List<PlatformTokenReq> tokenList) {
        if (tokenList.size() > 300 || CollectionUtils.isEmpty(tokenList)) {
            return new HashMap<>();
        }
        String tokenIds = tokenList.stream()
                .map(PlatformTokenReq::getCoingeckoCoinId)
                .collect(Collectors.joining(","));

        Map<String, TokenPriceResp> map = coinGeckoClient.getPlatformTokenPrice(tokenIds);
        return CollectionUtils.isEmpty(map)?new HashMap<>():map;
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
            CompletableFuture<CoinInfoResp> onlineTokenInfoFuture = CompletableFuture.supplyAsync(() -> coinGeckoClient.getPlatformCoinInfo(token.getCoingeckoCoinId()));
            CompletableFuture<Map<String, TokenPriceResp>> platformTokenPriceMapFuture = CompletableFuture.supplyAsync(() -> coinGeckoClient.getPlatformTokenPrice(token.getCoingeckoCoinId()));
            Map<String, TokenPriceResp> platformTokenPriceMap = platformTokenPriceMapFuture.get();
            TokenPriceResp onlineTokenPrice = platformTokenPriceMap.get(token.getCoingeckoCoinId());
            if (CollectionUtils.isEmpty(platformTokenPriceMap)) {
                return resultMap;
            }
            CoinInfoResp onlineTokenInfo = onlineTokenInfoFuture.get();
            // 不同平台,循环遍历
            onlineTokenInfo.getPlatforms().forEach((assetPlatformId, address) -> {
                try {
                    TokenCategoryCoinGeckoDTO coinGeckoDTO = new TokenCategoryCoinGeckoDTO();
                    //没有assetPlatformId
                    boolean isNative = !StringUtils.hasText(assetPlatformId) && !StringUtils.hasText(address);
                    if (isNative) {
                        coinGeckoDTO.setIsNative(true);
                        List<ChainCoinGeckoEnum> chainCoinGeckoEnums = ChainUtil.getNativeIdToEnum().getOrDefault(token.getCoingeckoCoinId(), new ArrayList<>());
                        for (ChainCoinGeckoEnum coinGeckoEnum : chainCoinGeckoEnums) {
                            setPlatformInfo(coinGeckoEnum, coinGeckoDTO, onlineTokenInfo, onlineTokenPrice, resultMap, false);
                        }
                    } else {
                        ChainCoinGeckoEnum coinGeckoEnum = ChainUtil.getCoinGeckoChainInfoMap().getOrDefault(assetPlatformId, null);
                        setPlatformInfo(coinGeckoEnum, coinGeckoDTO, onlineTokenInfo, onlineTokenPrice, resultMap, false);
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

    private void batchSetPlatformInfo(List<ChainCoinGeckoEnum> coinGeckoEnumList,
                                      TokenCategoryCoinGeckoDTO coinGeckoDTO,
                                      CoinInfoResp onlineTokenInfo,
                                      TokenPriceResp onlineTokenPrice,
                                      Map<String, TokenInfoDTO> resultMap,
                                      boolean isNative) {
        if(CollectionUtils.isEmpty(coinGeckoEnumList)){
            return;
        }
        List<TokenCategoryCoinGeckoDTO> tokenCategoryUpdateList=new ArrayList<>();
        List<TokenPriceDTO> tokenPriceDTOUpdateList=new ArrayList<>();
        List<TokenInfoDTO> tokenInfoDTOUpdateList=new ArrayList<>();
        for (ChainCoinGeckoEnum coinGeckoEnum : coinGeckoEnumList) {
            coinGeckoDTO.setIsNative(isNative);
            if (coinGeckoEnum != null) {
                coinGeckoDTO.setChainId(coinGeckoEnum.getChainId());
                TokenPriceDTO tokenPriceDTO = new TokenPriceDTO();
                CoinInfoResp.DetailPlatform detailPlatform = onlineTokenInfo.getDetailPlatforms().getOrDefault(coinGeckoEnum.getCoinGeckoEnum().getPlatformChainId(),null);
                setTokenInfoAndPrice(coinGeckoEnum.getChainId(), detailPlatform, onlineTokenInfo, onlineTokenPrice, coinGeckoDTO, tokenPriceDTO);
                TokenInfoDTO tokenInfoDTO = convertTokenInfo(coinGeckoDTO, tokenPriceDTO, new TokenRiskGoplusDTO());
                tokenCategoryUpdateList.add(coinGeckoDTO);
                tokenPriceDTOUpdateList.add(tokenPriceDTO);
                tokenInfoDTOUpdateList.add(tokenInfoDTO);
                resultMap.put(ChainUtil.getCommonKey(tokenInfoDTO.getChainId(), tokenInfoDTO.getAddress()), tokenInfoDTO);
            }
        }
        //批量更新 可以异步优化
        tokenCategoryDataService.batchInsertOrUpdate(tokenCategoryUpdateList);
        tokenPriceDataService.batchInsertOrUpdate(tokenPriceDTOUpdateList);
        tokenInfoService.batchInsertOrUpdate(tokenInfoDTOUpdateList);
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
            CoinInfoResp.DetailPlatform detailPlatform = onlineTokenInfo.getDetailPlatforms().getOrDefault(coinGeckoEnum.getCoinGeckoEnum().getPlatformChainId(), null);
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

    public Map<String, TokenInfoDTO> updateOnchainPrice(Map<String, TokenInfoDTO> first) {
        List<OnchainTokenReq> tokenLists = first.values().stream().map(tokenInfoDTO -> new OnchainTokenReq(tokenInfoDTO.getChainId(), tokenInfoDTO.getAddress())).toList();
        Map<Long, List<OnchainTokenReq>> chainTokenMap =
                tokenLists.stream()
                        .filter(e -> StringUtils.hasText(e.getAddress()) && Objects.nonNull(e.getChainId()))
                        .collect(Collectors.groupingBy(OnchainTokenReq::getChainId));

        chainTokenMap.forEach((chainId, tokens) -> {
            // 分隔成一组组
            List<List<OnchainTokenReq>> partitions = getPartitions(tokens, 30);
            // 遍历每组
            for (List<OnchainTokenReq> partition : partitions) {
                List<String> list = partition.stream().map(OnchainTokenReq::getAddress).toList();
                ChainCoinGeckoEnum chainCoinGeckoEnum = ChainUtil.getChainAndCoinGeckoMap().get(chainId);
                DexTokensPriceResp dexTokensPriceResp = coinGeckoClient.batchGetTokenPrice(chainCoinGeckoEnum.getCoinGeckoEnum().getNetworkId(), listToString(list));
                Map<String, BigDecimal> tokenPrices = dexTokensPriceResp.getData().getAttributes().getTokenPrices();
                tokenPrices.forEach((key, value) -> {
                    TokenInfoDTO tokenInfoDTO = first.get(ChainUtil.getCommonKey(chainId, key));
                    if (tokenInfoDTO == null) {
                        // TODO Fix the capitalization and lowercase issues. To be optimized.
                        tokenInfoDTO = first.values().stream().filter(e -> e.getAddress().equalsIgnoreCase(key)).findFirst().orElse(null);
                        if (tokenInfoDTO == null) {
                            log.warn("updateOnchainPrice tokenInfoDTO is null");
                            return;
                        }
                    }
                    tokenInfoDTO.setRealPrice(value);
                    tokenInfoService.insertOrUpdate(tokenInfoDTO);

                    TokenPriceDTO tokenPriceDTO = new TokenPriceDTO();
                    tokenPriceDTO.setChainId(tokenInfoDTO.getChainId());
                    tokenPriceDTO.setCoingeckoChainId(tokenInfoDTO.getCoingeckoChainId());
                    tokenPriceDTO.setAddress(tokenInfoDTO.getAddress());
                    tokenPriceDTO.setPoolAddress(tokenInfoDTO.getPoolAddress());
                    tokenPriceDTO.setIsPoolBaseToken(tokenInfoDTO.getIsPoolBaseToken());
                    tokenPriceDTO.setLiquidityUsd(tokenInfoDTO.getLiquidityUsd());
                    tokenPriceDTO.setRealPrice(tokenInfoDTO.getRealPrice());
                    tokenPriceDTO.setVolume24h(tokenInfoDTO.getVolume24h());
                    tokenPriceDTO.setChange24h(tokenInfoDTO.getChange24h());
                    tokenPriceDTO.setMarketCap(tokenInfoDTO.getMarketCap());
                    tokenPriceDTO.setFdvUsd(tokenInfoDTO.getFdvUsd());
                    tokenPriceDTO.setTotalSupply(tokenInfoDTO.getTotalSupply());
                    tokenPriceDataService.insertOrUpdate(tokenPriceDTO);
                });
            }
        });
        return first;
    }

    @Override
    public List<TokenOhlcvDTO> getPlatformTokenOhlcv(Long chainId, String address, IntervalEnum interval) {
        return kLineService.getTokenOhlcv(chainId, address, interval);
    }

    @Override
    public List<CoinPriceResp> batchOnchainCoinPrice(List<OnchainTokenReq> rawTokenList, boolean b) {
//        Map<String, TokenInfoDTO> resultMap = new ConcurrentHashMap<>();
        List<CoinPriceResp> resultList = new ArrayList<>();
        if (CollectionUtils.isEmpty(rawTokenList) || rawTokenList.size() > 300) {
            return resultList;
        }

        // 数据库查
        List<TokenBase> list = rawTokenList.stream().map(e -> new TokenBase(e.getAddress(), e.getChainId())).toList();
        Pair<Map<String, TokenInfoDTO>, List<TokenBase>> tokenInfoFromDB = getTokenInfoFromDB(list);
        // 已经存在了的结果
        Map<String, TokenInfoDTO> first = tokenInfoFromDB.getFirst();
        // 不存在的结果
        List<TokenBase> second = tokenInfoFromDB.getSecond();


        if (!CollectionUtils.isEmpty(first)) {
            Map<String, TokenInfoDTO> existMap = updateOnchainPrice(first);
            existMap.values().stream().map(e -> {
                CoinPriceResp coinPriceResp = buildCoinPriceResp(e);
                return coinPriceResp;
            }).forEach(resultList::add);
        }


        List<OnchainTokenReq> tokenList = second.stream().map(e -> new OnchainTokenReq(e.getChainId(), e.getAddress())).toList();

        Map<Long, List<OnchainTokenReq>> chainTokenMap = tokenList.stream()
                .filter(e -> StringUtils.hasText(e.getAddress()) && Objects.nonNull(e.getChainId()))
                .collect(Collectors.groupingBy(OnchainTokenReq::getChainId));
        List<OnchainTokenReq> nativeTokenList = tokenList.stream()
                .filter(e -> !StringUtils.hasText(e.getAddress()) && Objects.nonNull(e.getChainId()))
                .toList();

        chainTokenMap.forEach((chainId, tokens) -> {
            // 分隔成一组组
            List<List<OnchainTokenReq>> partitions = getPartitions(tokens, 30);
            // 遍历每组
            for (List<OnchainTokenReq> partition : partitions) {
                Map<String, TokenInfoDTO> map = singleOnchainTokenInfoAndPrice(partition, false);
                for (TokenInfoDTO dto : map.values()) {
                    CoinPriceResp coinPriceResp = buildCoinPriceResp(dto);
                    resultList.add(coinPriceResp);
                }
            }
        });
        List<PlatformTokenReq> platFormTokenReqList = nativeTokenList
                .stream()
                .map(e -> {
                    PlatformTokenReq tokenReq = new PlatformTokenReq();
                    ChainCoinGeckoEnum chainCoinGeckoEnum = ChainUtil.getChainAndCoinGeckoMap().get(e.getChainId());
                    if (chainCoinGeckoEnum != null) {
                        tokenReq.setCoingeckoCoinId(chainCoinGeckoEnum.getCoinGeckoEnum().getCoinId());
                    } else {
                        log.error("CoinGeckoServiceImpl batchOnchainCoinInfoAndPrice chainId not exist:{}", e.getChainId());
                    }
                    return tokenReq;
                }).toList();
        Map<String, TokenPriceResp> nativeTokenPriceMap = batchPlatformPrice(platFormTokenReqList);
        if (!CollectionUtils.isEmpty(nativeTokenPriceMap)) {
            for (OnchainTokenReq req : nativeTokenList) {
                ChainCoinGeckoEnum chainCoinGeckoEnum = ChainUtil.getChainAndCoinGeckoMap().get(req.getChainId());
                if (chainCoinGeckoEnum == null) {
                    continue;
                }
                String coinGeckoCoinId = chainCoinGeckoEnum.getCoinGeckoEnum().getCoinId();
                TokenPriceResp tokenPrice = nativeTokenPriceMap.get(coinGeckoCoinId);
                if (tokenPrice == null) {
                    continue;
                }
                CoinPriceResp coinPriceResp = new CoinPriceResp();
                coinPriceResp.setChainId(req.getChainId());
                coinPriceResp.setAddress("");
                coinPriceResp.setIsNative(true);
                coinPriceResp.setRealPrice(tokenPrice.getRealPrice() == null ? null : BigDecimal.valueOf(tokenPrice.getRealPrice()));
                coinPriceResp.setVolume24h(tokenPrice.getVolume24h() == null ? null : BigDecimal.valueOf(tokenPrice.getVolume24h()));
                coinPriceResp.setChange24h(tokenPrice.getChange24h() == null ? null : BigDecimal.valueOf(tokenPrice.getChange24h()));
                resultList.add(coinPriceResp);
            }
        }
        return resultList;

    }

    @NotNull
    private static CoinPriceResp buildCoinPriceResp(TokenInfoDTO dto) {
        CoinPriceResp coinPriceResp = new CoinPriceResp();
        coinPriceResp.setChainId(dto.getChainId());
        coinPriceResp.setAddress(dto.getAddress());
        coinPriceResp.setIsNative(false);
        coinPriceResp.setRealPrice(dto.getRealPrice());
        coinPriceResp.setVolume24h(dto.getVolume24h());
        coinPriceResp.setChange24h(dto.getChange24h());
        return coinPriceResp;
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

    // 查询数据库
    public Pair<Map<String, TokenInfoDTO>, List<TokenBase>> getTokenInfoFromDB(List<TokenBase> tokenBases) {
        List<TokenInfoDTO> tokenInfoDTOS = tokenInfoService.batchQuery(tokenBases);
        Set<String> allCacheToken = tokenInfoDTOS.stream().map(TokenInfoDTO::getId).collect(Collectors.toSet());
        Map<String, TokenInfoDTO> infoDTOMap = tokenInfoDTOS.stream().collect(Collectors.toMap(TokenInfoDTO::getId, Function.identity()));
        List<TokenBase> list = tokenBases.stream().filter(a -> !allCacheToken.contains(a.getId())).toList();
        return Pair.of(infoDTOMap, list);
    }
}
