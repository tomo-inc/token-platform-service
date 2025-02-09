package com.tomo.service.price.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomo.mapper.TokenPriceMapper;
import com.tomo.model.dto.TokenPriceDTO;
import com.tomo.service.price.TokenPriceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenPriceDataServiceImpl extends ServiceImpl<TokenPriceMapper, TokenPriceDTO> implements TokenPriceDataService {
    @Autowired
    private TokenPriceMapper tokenPriceMapper;

    @Override
    public boolean insertOrUpdate(TokenPriceDTO tokenPriceDTO){
        return tokenPriceMapper.insertOrUpdate(tokenPriceDTO);
    }

    @Override
    public void batchInsertOrUpdate(List<TokenPriceDTO> tokenPriceDTOUpdateList) {
         tokenPriceMapper.batchInsertOrUpdate(tokenPriceDTOUpdateList);
    }
}
