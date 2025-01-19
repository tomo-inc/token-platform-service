package com.tomo.service.category.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tomo.mapper.TokenInfoMapper;
import com.tomo.model.TokenBase;
import com.tomo.model.dto.TokenInfoDTO;
import com.tomo.service.category.TokenInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenInfoServiceImpl extends ServiceImpl<TokenInfoMapper, TokenInfoDTO> implements TokenInfoService {

    @Autowired
    private TokenInfoMapper tokenInfoMapper;

    @Override
    public List<TokenInfoDTO> batchQuery(List<TokenBase> tokenBase){
        return tokenInfoMapper.batchQuery(tokenBase);
    }

    @Override
    public boolean insertOrUpdate(TokenInfoDTO tokenInfoDTO) {
        return tokenInfoMapper.insertOrUpdate(tokenInfoDTO);
    }

    @Override
    public TokenInfoDTO exactQueryToken(Long chainId, String address) {
        LambdaQueryWrapper<TokenInfoDTO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TokenInfoDTO::getChainId, chainId);
        queryWrapper.eq(TokenInfoDTO::getAddress, address);

        List<TokenInfoDTO> list = super.list(queryWrapper);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<TokenInfoDTO> fuzzQueryPlatformToken(Long chainId, String addressOrName) {
        LambdaQueryWrapper<TokenInfoDTO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(TokenInfoDTO::getName, "%" + addressOrName + "%")
                .or()
                .eq(TokenInfoDTO::getAddress, addressOrName)
                .or()
                .eq(TokenInfoDTO::getSymbol, addressOrName)
                .orderByDesc(TokenInfoDTO::getVolume24h)
                .last("LIMIT 30");
        if (chainId != null) {
            queryWrapper.eq(TokenInfoDTO::getChainId, chainId);
        }
        return super.list(queryWrapper);
    }
}
