package com.tomo.service.token;

import com.tomo.feign.BackendClient;
import com.tomo.model.dto.TokenDTO;
import com.tomo.model.resp.BackendResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TokenService {
    @Autowired
    private BackendClient backendClient;

    public List<TokenDTO> tokenSearch(String authorization, String content, String chain) {
        BackendResponseDTO<List<TokenDTO>> backEndTokenSearchRes = backendClient.tokenSearch(authorization, content, chain);
        List<TokenDTO> backEndTokenList = backEndTokenSearchRes.getResult();
        return backEndTokenList;
    }
}
