package com.tomo.service.category.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomo.mapper.TokenCategoryMapper;
import com.tomo.model.dto.TokenCategoryCoinGeckoDTO;
import com.tomo.service.category.TokenCategoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenCategoryDataServiceImpl extends ServiceImpl<TokenCategoryMapper, TokenCategoryCoinGeckoDTO> implements TokenCategoryDataService {
    @Autowired
    TokenCategoryMapper tokenCategoryMapper;

    @Override
    public boolean insertOrUpdate(TokenCategoryCoinGeckoDTO tokenCategoryCoinGeckoDTO){
        return tokenCategoryMapper.insertOrUpdate(tokenCategoryCoinGeckoDTO);
    }
}
