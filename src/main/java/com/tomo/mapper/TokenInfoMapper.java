package com.tomo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomo.model.dto.TokenInfoDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TokenInfoMapper extends BaseMapper<TokenInfoDTO> {
    @Insert({
          """
           INSERT INTO token_info (
              chain_id, address, coingecko_coin_id,coingecko_chain_id, is_native, name, display_name, symbol, image_url, decimals, website_url, twitter_url, telegram_url,pool_address,is_pool_base_token,liquidity_usd, real_price, volume24h, change24h, market_cap, fdv_usd,total_supply,risk_level
           ) VALUES (
            #{tokenInfoDTO.chainId},
            #{tokenInfoDTO.address},
            #{tokenInfoDTO.coingeckoCoinId},
            #{tokenInfoDTO.coingeckoChainId},
            #{tokenInfoDTO.isNative},
            #{tokenInfoDTO.name},
            #{tokenInfoDTO.displayName},
            #{tokenInfoDTO.symbol},
            #{tokenInfoDTO.imageUrl},
            #{tokenInfoDTO.decimals},
            #{tokenInfoDTO.websiteUrl},
            #{tokenInfoDTO.twitterUrl},
            #{tokenInfoDTO.telegramUrl},
            #{tokenInfoDTO.poolAddress},
            #{tokenInfoDTO.isPoolBaseToken},
            #{tokenInfoDTO.realPrice},
            #{tokenInfoDTO.liquidityUsd},
            #{tokenInfoDTO.volume24h},
            #{tokenInfoDTO.change24h},
            #{tokenInfoDTO.marketCap},
            #{tokenInfoDTO.fdvUsd},
            #{tokenInfoDTO.totalSupply},
            #{tokenInfoDTO.riskLevel}) ON DUPLICATE KEY UPDATE
            chain_id = IF(#{tokenInfoDTO.chainId} IS NOT NULL, #{tokenInfoDTO.chainId},chain_id),
            address = IF(#{tokenInfoDTO.address} IS NOT NULL, #{tokenInfoDTO.address}, address),
            coingecko_coin_id = IF(#{tokenInfoDTO.coingeckoCoinId} IS NOT NULL, #{tokenInfoDTO.coingeckoCoinId}, coingecko_coin_id),
            coingecko_chain_id = IF(#{tokenInfoDTO.coingeckoChainId} IS NOT NULL, #{tokenInfoDTO.coingeckoChainId}, coingecko_chain_id),
            is_native = IF(#{tokenInfoDTO.isNative} IS NOT NULL, #{tokenInfoDTO.isNative}, is_native),
            name = IF(#{tokenInfoDTO.name} IS NOT NULL, #{tokenInfoDTO.name}, name),
            display_name = IF(#{tokenInfoDTO.displayName} IS NOT NULL, #{tokenInfoDTO.displayName}, display_name),
            symbol = IF(#{tokenInfoDTO.symbol} IS NOT NULL, #{tokenInfoDTO.symbol}, symbol),
            image_url = IF(#{tokenInfoDTO.imageUrl} IS NOT NULL, #{tokenInfoDTO.imageUrl}, image_url),
            decimals = IF(#{tokenInfoDTO.decimals} IS NOT NULL, #{tokenInfoDTO.decimals}, decimals),
            website_url = IF(#{tokenInfoDTO.websiteUrl} IS NOT NULL, #{tokenInfoDTO.websiteUrl}, website_url),
            twitter_url = IF(#{tokenInfoDTO.twitterUrl} IS NOT NULL, #{tokenInfoDTO.twitterUrl}, twitter_url),
            telegram_url = IF(#{tokenInfoDTO.telegramUrl} IS NOT NULL, #{tokenInfoDTO.telegramUrl}, telegram_url),
            pool_address = IF(#{tokenInfoDTO.poolAddress} IS NOT NULL, #{tokenInfoDTO.poolAddress},pool_address),
            is_pool_base_token = IF(#{tokenInfoDTO.isPoolBaseToken} IS NOT NULL, #{tokenInfoDTO.isPoolBaseToken}, is_pool_base_token),
            liquidity_usd = IF(#{tokenInfoDTO.liquidityUsd} IS NOT NULL, #{tokenInfoDTO.liquidityUsd}, liquidity_usd),
            real_price = IF(#{tokenInfoDTO.realPrice} IS NOT NULL, #{tokenInfoDTO.realPrice}, real_price),
            volume24h = IF(#{tokenInfoDTO.volume24h} IS NOT NULL, #{tokenInfoDTO.volume24h}, volume24h),
            change24h = IF(#{tokenInfoDTO.change24h} IS NOT NULL, #{tokenInfoDTO.change24h},change24h),
            market_cap = IF(#{tokenInfoDTO.marketCap} IS NOT NULL, #{tokenInfoDTO.marketCap}, market_cap),
            fdv_usd = IF(#{tokenInfoDTO.fdvUsd} IS NOT NULL, #{tokenInfoDTO.fdvUsd}, fdv_usd),
            total_supply = IF(#{tokenInfoDTO.totalSupply} IS NOT NULL, #{tokenInfoDTO.totalSupply}, total_supply),
            risk_level = IF(#{tokenInfoDTO.riskLevel} IS NOT NULL, #{tokenInfoDTO.riskLevel}, risk_level)
          """
    })
    boolean insertOrUpdate(@Param("tokenInfoDTO") TokenInfoDTO tokenInfoDTO);
}