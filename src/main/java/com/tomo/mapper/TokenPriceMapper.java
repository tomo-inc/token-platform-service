package com.tomo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomo.model.dto.TokenPriceDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TokenPriceMapper extends BaseMapper<TokenPriceDTO> {

    @Insert({
            """
            INSERT INTO token_price (
                coin_id, chain_id, address, pool_address,is_pool_base_token,liquidity_usd, real_price, volume24h, change24h, market_cap, fdv_usd,total_supply
            ) VALUES (
                #{tokenPriceDTO.coinId},#{tokenPriceDTO.chainId}, #{tokenPriceDTO.address}, #{tokenPriceDTO.poolAddress}, #{tokenPriceDTO.isPoolBaseToken}, #{tokenPriceDTO.realPrice}, #{tokenPriceDTO.liquidityUsd}, #{tokenPriceDTO.volume24h}, #{tokenPriceDTO.change24h}, #{tokenPriceDTO.marketCap}, #{tokenPriceDTO.fdvUsd}, #{tokenPriceDTO.totalSupply}
            ) ON DUPLICATE KEY UPDATE
               coin_id = IF(#{tokenPriceDTO.coinId} IS NOT NULL, #{tokenPriceDTO.coinId}, coin_id),
               chain_id = IF(#{tokenPriceDTO.chainId} IS NOT NULL, #{tokenPriceDTO.chainId}, chain_id),
               address = IF(#{tokenPriceDTO.address} IS NOT NULL, #{tokenPriceDTO.address}, address),
               pool_address = IF(#{tokenPriceDTO.poolAddress} IS NOT NULL, #{tokenPriceDTO.poolAddress}, pool_address),
               is_pool_base_token = IF(#{tokenPriceDTO.isPoolBaseToken} IS NOT NULL, #{tokenPriceDTO.isPoolBaseToken}, is_pool_base_token),
               liquidity_usd = IF(#{tokenPriceDTO.liquidityUsd} IS NOT NULL, #{tokenPriceDTO.liquidityUsd}, liquidity_usd),
               real_price = IF(#{tokenPriceDTO.realPrice} IS NOT NULL, #{tokenPriceDTO.realPrice}, real_price),
               volume24h = IF(#{tokenPriceDTO.volume24h} IS NOT NULL, #{tokenPriceDTO.volume24h}, volume24h),
               change24h = IF(#{tokenPriceDTO.change24h} IS NOT NULL, #{tokenPriceDTO.change24h}, change24h),
               market_cap = IF(#{tokenPriceDTO.marketCap} IS NOT NULL, #{tokenPriceDTO.marketCap}, market_cap),
               fdv_usd = IF(#{tokenPriceDTO.fdvUsd} IS NOT NULL, #{tokenPriceDTO.fdvUsd}, fdv_usd),
               total_supply = IF(#{tokenPriceDTO.totalSupply} IS NOT NULL, #{tokenPriceDTO.totalSupply}, total_supply)
            """
    })
    boolean insertOrUpdate(@Param("tokenPriceDTO") TokenPriceDTO tokenPriceDTO);
}