package com.tomo.service.risk.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tomo.feign.GoplusClient;
import com.tomo.model.dto.GoplusResultConvert;
import com.tomo.model.dto.TokenRiskGoplusDTO;
import com.tomo.model.req.RiskTokenDataReq;
import com.tomo.model.resp.SolanaSecurityResp;
import com.tomo.service.risk.RiskTokenDataService;
import com.tomo.service.risk.TokenRiskGoplusService;
import io.gopluslabs.client.ApiException;
import io.gopluslabs.client.GoPlusClient;
import io.gopluslabs.client.auth.SignatureOauth;
import io.gopluslabs.client.model.ResponseWrapperTokenSecurityResult;
import io.gopluslabs.client.request.AccessTokenRequest;
import io.gopluslabs.client.request.TokenSecurityRequest;
import io.gopluslabs.client.response.AccessToken;
import io.gopluslabs.client.response.TokenSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GoplusRiskTokenDataServiceImpl implements RiskTokenDataService<TokenRiskGoplusDTO> {

    private static final String goplusKey = "";

    private static final String goplusSecret = "";

    @Autowired
    TokenRiskGoplusService tokenRiskGoplusService;

    @Autowired
    GoplusClient goplusClient;

    @Override
    public TokenRiskGoplusDTO fetchRiskToken(RiskTokenDataReq query) {
        if (query == null || query.getChainId() == null || !StringUtils.hasText(query.getAddress())) {
            return null;
        }
        try {
            String goplusToken = getGoplusToken();
            List<TokenRiskGoplusDTO> goplusResults;
            // solana
            if (501 == (query.getChainId())) {
                SolanaSecurityResp solanaSecurity = goplusClient.getSolanaSecurity(query.getAddress(), goplusToken);
                goplusResults = solanaSecurity.getResult().entrySet().stream()
                        .filter(result -> query.getAddress().equals(result.getKey()))
                        .map(result -> getGoplusResult(query, result.getValue())
                        ).toList();
            // eth
            }else {
                TokenSecurityRequest request = TokenSecurityRequest.of(query.getChainId().toString(), query.getAddress(),goplusToken, 10000);
                TokenSecurity tokenSecurity = GoPlusClient.tokenSecurity(request);
                Map<String, ResponseWrapperTokenSecurityResult> tokenSecurityResultMap = tokenSecurity.getValue().getResult();
                goplusResults = tokenSecurityResultMap
                        .entrySet().stream()
                        .filter(result -> query.getAddress().equals(result.getKey()))
                        .map(result -> getGoplusResult(query, result.getValue())
                        ).toList();
            }
            return goplusResults.isEmpty() ? null : goplusResults.get(0);
        } catch (ApiException e) {
            log.error("fetchRiskToken error", e);
        }
        return null;
    }


    @Override
    public boolean saveRiskToken(TokenRiskGoplusDTO riskToken) {
        return tokenRiskGoplusService.save(riskToken);
    }

    @Override
    public boolean updateRiskToken(TokenRiskGoplusDTO riskToken) {
        LambdaUpdateWrapper<TokenRiskGoplusDTO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TokenRiskGoplusDTO::getChainId, riskToken.getChainId())
                     .eq(TokenRiskGoplusDTO::getAddress, riskToken.getAddress());
        return tokenRiskGoplusService.update(riskToken,updateWrapper);
    }

    @Override
    public TokenRiskGoplusDTO getRiskToken(RiskTokenDataReq riskTokenList) {
        LambdaQueryWrapper<TokenRiskGoplusDTO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TokenRiskGoplusDTO::getChainId, riskTokenList.getChainId())
                    .eq(TokenRiskGoplusDTO::getAddress, riskTokenList.getAddress());
        List<TokenRiskGoplusDTO> list = tokenRiskGoplusService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

//    @Cacheable(value = Constants.Cache.GOPLUS_TOKEN_BY_ONE_HOUR)
    public String getGoplusToken(){
        Long time = System.currentTimeMillis() / 1000;
        String sign = SignatureOauth.signatureSha1(goplusKey, time, goplusSecret);
        AccessTokenRequest of = AccessTokenRequest.of(goplusKey, sign, time, 1000);
        try {
            AccessToken accessToken = GoPlusClient.getAccessToken(of);
            return accessToken.getValue().getResult().getAccessToken();
        }catch (Exception e){
            log.error("getGoplusToken error: ", e);
            return null;
        }
    }


    private static TokenRiskGoplusDTO getGoplusResult(RiskTokenDataReq query, ResponseWrapperTokenSecurityResult result) {
        TokenRiskGoplusDTO goplusResult = GoplusResultConvert.covert(result);
        goplusResult.setAddress(query.getAddress());
        goplusResult.setChainId(query.getChainId());
        return goplusResult;
    }

    private static TokenRiskGoplusDTO getGoplusResult(RiskTokenDataReq query, SolanaSecurityResp.SolanaTokenDetails result) {
        TokenRiskGoplusDTO goplusResult = GoplusResultConvert.covert(result);
        goplusResult.setAddress(query.getAddress());
        goplusResult.setChainId(query.getChainId());
        return goplusResult;
    }

}
