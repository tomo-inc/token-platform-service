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
            ) 
            ON CONFLICT (chain_id,address) 
            DO UPDATE SET
               coin_id = COALESCE(#{tokenPriceDTO.coinId}, token_price.coin_id),
               chain_id = COALESCE(#{tokenPriceDTO.chainId}, token_price.chain_id),
               address = COALESCE(#{tokenPriceDTO.address}, token_price.address),
               pool_address = COALESCE(#{tokenPriceDTO.poolAddress}, token_price.pool_address),
               is_pool_base_token = COALESCE(#{tokenPriceDTO.isPoolBaseToken}, token_price.is_pool_base_token),
               liquidity_usd = COALESCE(#{tokenPriceDTO.liquidityUsd}, token_price.liquidity_usd),
               real_price = COALESCE(#{tokenPriceDTO.realPrice}, token_price.real_price),
               volume24h = COALESCE(#{tokenPriceDTO.volume24h}, token_price.volume24h),
               change24h = COALESCE(#{tokenPriceDTO.change24h}, token_price.change24h),
               market_cap = COALESCE(#{tokenPriceDTO.marketCap}, token_price.market_cap),
               fdv_usd = COALESCE(#{tokenPriceDTO.fdvUsd}, token_price.fdv_usd),
               total_supply = COALESCE(#{tokenPriceDTO.totalSupply}, token_price.total_supply)
            """
    })
    boolean insertOrUpdate(@Param("tokenPriceDTO") TokenPriceDTO tokenPriceDTO);
}