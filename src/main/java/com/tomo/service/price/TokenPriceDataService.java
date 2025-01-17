package com.tomo.service.price;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tomo.model.dto.TokenPriceDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

public interface TokenPriceDataService extends IService<TokenPriceDTO>{

    @Override
    default boolean updateById(TokenPriceDTO entity) {
        LambdaUpdateWrapper<TokenPriceDTO> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(TokenPriceDTO::getAddress, entity.getAddress());
        queryWrapper.eq(TokenPriceDTO::getChainId, entity.getChainId());

        if(Objects.nonNull(entity.getAddress())){
            queryWrapper.set(TokenPriceDTO::getAddress, entity.getAddress());
        }
        if(Objects.nonNull(entity.getPoolAddress())){
            queryWrapper.set(TokenPriceDTO::getPoolAddress, entity.getPoolAddress());
        }
        if(Objects.nonNull(entity.getIsPoolBaseToken())){
            queryWrapper.set(TokenPriceDTO::getIsPoolBaseToken,entity.getIsPoolBaseToken());
        }
        if(Objects.nonNull(entity.getLiquidityUsd())){
            queryWrapper.set(TokenPriceDTO::getLiquidityUsd, entity.getLiquidityUsd());
        }
        if(Objects.nonNull(entity.getRealPrice())){
            queryWrapper.set(TokenPriceDTO::getRealPrice, entity.getRealPrice());
        }
        if(Objects.nonNull(entity.getVolume24h())){
            queryWrapper.set(TokenPriceDTO::getVolume24h, entity.getVolume24h());
        }
        if(Objects.nonNull(entity.getChange24h())){
            queryWrapper.set(TokenPriceDTO::getChange24h, entity.getChange24h());
        }
        if(Objects.nonNull(entity.getMarketCap())){
            queryWrapper.set(TokenPriceDTO::getMarketCap, entity.getMarketCap());
        }
        if(Objects.nonNull(entity.getFdvUsd())){
            queryWrapper.set(TokenPriceDTO::getFdvUsd, entity.getFdvUsd());
        }
        if(Objects.nonNull(entity.getTotalSupply())){
            queryWrapper.set(TokenPriceDTO::getTotalSupply, entity.getTotalSupply());
        }
        return IService.super.update(entity, queryWrapper);
    }

    default TokenPriceDTO listOne(Long chainId, String address) {
        LambdaQueryWrapper<TokenPriceDTO> queryWrapper  = new LambdaQueryWrapper<>();
        queryWrapper.eq(TokenPriceDTO::getChainId, chainId);
        queryWrapper.eq(TokenPriceDTO::getAddress, address);
        List<TokenPriceDTO> list = IService.super.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    boolean insertOrUpdate(TokenPriceDTO tokenPriceDTO);
}
