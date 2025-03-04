package com.tomo.service.token;

import chain_quote_indexer.ChainQuoteIndexerOuterClass;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tomo.feign.BackendClient;
import com.tomo.feign.FourMemeClient;
import com.tomo.grpc.ChainQuoteIndexerClient;
import com.tomo.mapper.FourMemeTokenMapper;
import com.tomo.model.ChainEnum;
import com.tomo.model.convert.MemeTokenConverter;
import com.tomo.model.dto.FourMemeToken;
import com.tomo.model.dto.MemeTokenDTO;
import com.tomo.model.dto.TokenDTO;
import com.tomo.model.resp.BackendResponseDTO;
import com.tomo.model.resp.Result;
import com.tomo.model.resp.TokenInfoRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TokenService {
    @Autowired
    private BackendClient backendClient;
    @Autowired
    private FourMemeClient fourMemeClient;
    @Autowired
    private FourMemeTokenMapper fourMemeTokenMapper;
    @Autowired
    private ChainQuoteIndexerClient chainQuoteIndexerClient;

    public List<TokenDTO> tokenSearch(String authorization, String content, String chain) {
        Set<String> nameSet = new HashSet<>();
        List<TokenDTO> dataList = new ArrayList<>();
        BackendResponseDTO<List<TokenDTO>> backEndTokenSearchRes = backendClient.tokenSearch(authorization, content, chain);
        LambdaQueryWrapper<FourMemeToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(FourMemeToken::getTokenName, "%" + content + "%")
                .or()
                .eq(FourMemeToken::getTokenAddress, content.toLowerCase());
        queryWrapper.eq(FourMemeToken::getLaunchOnPancake, false);
        List<FourMemeToken> fourMemeTokens = fourMemeTokenMapper.selectList(queryWrapper);
        List<TokenDTO> backEndTokenList = backEndTokenSearchRes.getResult();

        if(!CollectionUtils.isEmpty(backEndTokenList)){
            dataList.addAll(backEndTokenList);
            nameSet.addAll(backEndTokenList.stream().map(TokenDTO::getName).collect(Collectors.toSet()));
        }
        if (!CollectionUtils.isEmpty(fourMemeTokens)) {
            List<TokenDTO> collect = fourMemeTokens.stream().map(TokenService::transferToTokenDTO).filter(data -> !nameSet.contains(data.getName())).collect(Collectors.toList());
            dataList.addAll(collect);
        }

        this.completeDataByQuote(dataList);
        return dataList;
    }

    public TokenDTO tokenDetail(String authorization, String tokenName) {
        String[] splitArray = tokenName.split("-");
        LambdaQueryWrapper<FourMemeToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FourMemeToken::getTokenAddress, splitArray[1].toLowerCase());
        List<FourMemeToken> fourMemeTokens = fourMemeTokenMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(fourMemeTokens)){
            FourMemeToken fourMemeToken = fourMemeTokens.get(0);
            TokenDTO tokenDTO = transferToTokenDTO(fourMemeToken);
            Result<TokenInfoRes> tokenRes = fourMemeClient.getToken(tokenDTO.getAddress());
            if(Objects.nonNull(tokenRes) && Objects.nonNull(tokenRes.getData())){
                TokenInfoRes data = tokenRes.getData();
                String totalAmount = data.getTotalAmount();
                String saleAmount = data.getSaleAmount();
                String price = data.getTokenPrice().getPrice();
                tokenDTO.setTotalSupply(StringUtils.isNotBlank(totalAmount) ? new BigDecimal(totalAmount) : BigDecimal.ZERO);
                tokenDTO.setMarketCapUsd(tokenDTO.getTotalSupply().multiply(StringUtils.isNotBlank(price) ? new BigDecimal(price) : BigDecimal.ZERO));
                tokenDTO.setFdvUsd(new BigDecimal(saleAmount).multiply(StringUtils.isNotBlank(price) ? new BigDecimal(price) : BigDecimal.ZERO));
                if(StringUtils.isBlank(fourMemeToken.getImageUrl())){
                    FourMemeToken updateToken = new FourMemeToken();
                    updateToken.setId(fourMemeToken.getId());
                    updateToken.setImageUrl(data.getImage());
                    updateToken.setTokenName(fourMemeToken.getTokenName());
                    fourMemeTokenMapper.updateById(updateToken);

                }
                tokenDTO.setImageUrl(data.getImage());
                String raiseTokenName = StringUtils.isNotBlank(fourMemeToken.getRaiseTokenAddress()) ? "BSC-"+fourMemeToken.getRaiseTokenAddress() : fourMemeToken.getRaiseTokenSymbol();
                BackendResponseDTO<TokenDTO> backEndTokenSearchRes = backendClient.tokenDetail(authorization, raiseTokenName);
                TokenDTO result = backEndTokenSearchRes.getResult();
                if(Objects.isNull(result)){
                    tokenDTO.setRaiseValue(new BigDecimal(data.getRaisedAmount()));
                }else{
                    tokenDTO.setRaiseValue(result.getPriceUsd().multiply(new BigDecimal(data.getRaisedAmount())));
                }
            }

            return tokenDTO;
        }
        BackendResponseDTO<TokenDTO> backEndTokenSearchRes = backendClient.tokenDetail(authorization, tokenName);
        TokenDTO result = backEndTokenSearchRes.getResult();

        this.completeDataByQuote(Collections.singletonList(result));
        return result;
    }

    private static TokenDTO transferToTokenDTO(FourMemeToken fourMemeToken) {
        TokenDTO tokenDTO = TokenDTO.builder()
                .progress(fourMemeToken.getProgress())
                .publishTime(fourMemeToken.getPublishTime())
                .riseTokenSymbol(fourMemeToken.getRaiseTokenSymbol())
                .riseTokenAddress(StringUtils.equalsIgnoreCase(fourMemeToken.getRaiseTokenAddress(),"BNB") ? "" : fourMemeToken.getRaiseTokenAddress())
                .fourMemeToken(true)
                .id(Long.valueOf(fourMemeToken.getId()))
                .name("BSC-"+fourMemeToken.getTokenAddress().toLowerCase())
                .displayName(fourMemeToken.getTokenName())
                .symbol(fourMemeToken.getTokenSymbol())
                .imageUrl(fourMemeToken.getImageUrl())
                .decimals(fourMemeToken.getTokenPrecision())
                .chain("BSC")
                .isNative(false)
                .address(fourMemeToken.getTokenAddress())
                .priceUsd(new BigDecimal(fourMemeToken.getPriceUsd()))
                .priceChangeH24(new BigDecimal(fourMemeToken.getPriceChangeH24()).doubleValue())
                .volumeH24(new BigDecimal(fourMemeToken.getVolumeH24()))
                .marketCapUsd(StringUtils.isNoneBlank(fourMemeToken.getMarketCapUsd()) ? new BigDecimal(fourMemeToken.getMarketCapUsd()) : BigDecimal.ZERO)
                .risk("SAFE")
                .launchOnPancake(fourMemeToken.getLaunchOnPancake())
                .build();
        return tokenDTO;
    }

    public List<MemeTokenDTO> memeTokenQuery(String status, Boolean launchOnPancake, String orderByField,String orderByRule) {
        List<MemeTokenDTO> dataList = new ArrayList<>();
        QueryWrapper<FourMemeToken> queryWrapper = getFourMemeTokenQueryWrapper(status, launchOnPancake, orderByField, orderByRule);
        List<FourMemeToken> fourMemeTokens = fourMemeTokenMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(fourMemeTokens)) {
            fourMemeTokens.forEach(data -> {
                MemeTokenDTO tokenDto = MemeTokenConverter.INSTANCE.toTokenDto(data);
                tokenDto.setDisplayName(data.getTokenName());
                tokenDto.setSymbol(data.getTokenSymbol());
                tokenDto.setDecimals(data.getTokenPrecision());
                tokenDto.setFourMemeToken(true);
                tokenDto.setVolumeWeiH24(data.getVolumeH24());
                dataList.add(tokenDto);
            });
        }

        this.completeDataByQuoteV2(dataList);
        return dataList;
    }

    @NotNull
    private static QueryWrapper<FourMemeToken> getFourMemeTokenQueryWrapper(String status, Boolean launchOnPancake, String orderByField, String orderByRule) {
        orderByField = getOrderByField(orderByField);
        QueryWrapper<FourMemeToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("launch_on_pancake", launchOnPancake);
        if(Objects.equals(status, "new")){
            queryWrapper.ge("progress", Double.valueOf(0d));
            queryWrapper.le("progress", Double.valueOf(0.6d));
            if(StringUtils.isNoneBlank(orderByField) && StringUtils.isNoneBlank(orderByRule)){
                if ("asc".equalsIgnoreCase(orderByRule)) {
                    queryWrapper.orderByAsc(orderByField);
                } else {
                    queryWrapper.orderByDesc(orderByField);
                }
            }else{
                queryWrapper.orderByDesc("publish_time");
            }
        }else if(Objects.equals(status, "early")){
            queryWrapper.ge("progress", Double.valueOf(0.6d));
            if(StringUtils.isNoneBlank(orderByField) && StringUtils.isNoneBlank(orderByRule)){
                if ("asc".equalsIgnoreCase(orderByRule)) {
                    queryWrapper.orderByAsc(orderByField);
                } else {
                    queryWrapper.orderByDesc(orderByField);
                }
            }else{
                queryWrapper.orderByDesc("progress");
            }
        }else{
            if(StringUtils.isNoneBlank(orderByField) && StringUtils.isNoneBlank(orderByRule)){
                if ("asc".equalsIgnoreCase(orderByRule)) {
                    queryWrapper.orderByAsc(orderByField);
                } else {
                    queryWrapper.orderByDesc(orderByField);
                }
            }else{
                queryWrapper.orderByDesc("price_change_h24");
            }

        }

        queryWrapper.last("limit 100");
        return queryWrapper;
    }
    private static String getOrderByField(String orderByField){
        if(StringUtils.equalsIgnoreCase(orderByField, "progress")){
            return "progress";
        }else if (StringUtils.equalsIgnoreCase(orderByField, "priceUsd")){
            return "price_usd";
        }else if (StringUtils.equalsIgnoreCase(orderByField, "priceChangeH24")){
            return "price_change_h24";
        }else if (StringUtils.equalsIgnoreCase(orderByField, "volumeH24")){
            return "volume_h24";
        }else if (StringUtils.equalsIgnoreCase(orderByField, "marketCapUsd")){
            return "market_cap_usd";
        }else if (StringUtils.equalsIgnoreCase(orderByField, "publishTime")){
            return "publish_time";
        }else{
            return null;
        }
    }


    public Boolean addToken(FourMemeToken fourMemeToken) {
        int rows = fourMemeTokenMapper.insert(fourMemeToken);
        return Objects.equals(rows, 1);
    }

    public Boolean updateToken(FourMemeToken fourMemeToken) {
        int rows = fourMemeTokenMapper.updateById(fourMemeToken);
        return Objects.equals(rows, 1);
    }
    public FourMemeToken querByAddress(String tokenAddress) {
        LambdaQueryWrapper<FourMemeToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FourMemeToken::getTokenAddress, tokenAddress);
        return fourMemeTokenMapper.selectOne(queryWrapper);
    }

    private void completeDataByQuote(List<TokenDTO> list){
        final int batchSize = 10;

        for (int i = 0; i < list.size(); i += batchSize) {
            List<TokenDTO> batch = list.subList(i, Math.min(i + batchSize, list.size()));
            List<String> addresses = batch.stream().map(TokenDTO::getAddress).collect(Collectors.toList());
            Map<String, ChainQuoteIndexerOuterClass.Quote> quotes = chainQuoteIndexerClient.findQuotes(
                    ChainEnum.BSC.getChainIndex(), addresses);

            if (MapUtil.isEmpty(quotes)) {
                return;
            }
            for (TokenDTO tokenDTO : batch) {
                String address = tokenDTO.getAddress();
                ChainQuoteIndexerOuterClass.Quote quote = quotes.get(address);
                if (quote != null) {
                    tokenDTO.setPriceChangeH24(quote.getChange());
                    tokenDTO.setVolumeH24(new BigDecimal(quote.getVolume24H()));
                }
            }
        }
    }

    private void completeDataByQuoteV2(List<MemeTokenDTO> list){
        final int batchSize = 10;

        for (int i = 0; i < list.size(); i += batchSize) {
            List<MemeTokenDTO> batch = list.subList(i, Math.min(i + batchSize, list.size()));
            List<String> addresses = batch.stream().map(MemeTokenDTO::getTokenAddress).collect(Collectors.toList());
            Map<String, ChainQuoteIndexerOuterClass.Quote> quotes = chainQuoteIndexerClient.findQuotes(
                    ChainEnum.BSC.getChainIndex(), addresses);

            if (MapUtil.isEmpty(quotes)) {
                return;
            }
            for (MemeTokenDTO tokenDTO : batch) {
                String address = tokenDTO.getTokenAddress();
                ChainQuoteIndexerOuterClass.Quote quote = quotes.get(address);
                if (quote != null) {
                    tokenDTO.setPriceChangeH24(Double.toString(quote.getChange()));
                    tokenDTO.setVolumeH24(quote.getVolume24H());
                }
            }
        }
    }
}
