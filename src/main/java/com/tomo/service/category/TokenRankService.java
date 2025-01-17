package com.tomo.service.category;

import com.tomo.model.dto.TokenRankDTO;

import java.util.List;

public interface TokenRankService {
    List<TokenRankDTO> getOKXTrending(String chainId);
}
