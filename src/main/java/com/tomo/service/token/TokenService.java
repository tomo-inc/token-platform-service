package com.tomo.service.token;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tomo.feign.BackendClient;
import com.tomo.mapper.FourMemeTokenMapper;
import com.tomo.model.dto.FourMemeToken;
import com.tomo.model.dto.TokenDTO;
import com.tomo.model.resp.BackendResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TokenService {
    @Autowired
    private BackendClient backendClient;
    @Autowired
    private FourMemeTokenMapper fourMemeTokenMapper;

    public List<TokenDTO> tokenSearch(String authorization, String content, String chain) {
        List<TokenDTO> dataList = new ArrayList<>();
        Date now = new Date();
        BackendResponseDTO<List<TokenDTO>> backEndTokenSearchRes = backendClient.tokenSearch(authorization, content, chain);
        LambdaQueryWrapper<FourMemeToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(FourMemeToken::getTokenName, "%" + content + "%")
                .or()
                .eq(FourMemeToken::getTokenAddress, content.toLowerCase());
        List<FourMemeToken> fourMemeTokens = fourMemeTokenMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(fourMemeTokens)) {
            List<TokenDTO> collect = fourMemeTokens.stream().map(data -> {
                TokenDTO tokenDTO = TokenDTO.builder()
                        .riseTokenSymbol(data.getRiskTokenSymbol())
                        .riseTokenAddress(StringUtils.equalsIgnoreCase(data.getRiskTokenSymbol(),"BNB") ? "" : data.getRiskTokenAddress())
                        .fourMemeToken(true)
                        .id(Long.valueOf(data.getId()))
                        .name("BSC-"+data.getTokenAddress())
                        .displayName(data.getTokenName())
                        .symbol(data.getTokenName())
                        .imageUrl(data.getImageUrl())
                        .decimals(data.getTokenPrecision())
                        .chain("BSC")
                        .isNative(false)
                        .address(data.getTokenAddress())
                        .priceUsd(new BigDecimal(data.getPriceUsd()))
                        .priceChangeH24(new BigDecimal(data.getPriceChangeH24()).doubleValue())
                        .volumeH24(new BigDecimal(data.getVolumeH24()))
                        .marketCapUsd(StringUtils.isNoneBlank(data.getMarketCapUsd()) ? new BigDecimal(data.getMarketCapUsd()) : BigDecimal.ZERO)
                        .risk("SAFE")
                        .createdTime(now)
                        .updatedTime(now)
                        .build();
                return tokenDTO;
            }).collect(Collectors.toList());
            dataList.addAll(collect);
        }

        List<TokenDTO> backEndTokenList = backEndTokenSearchRes.getResult();
        if(!CollectionUtils.isEmpty(backEndTokenList)){
            dataList.addAll(backEndTokenList);
        }
        return dataList;
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
}
