package com.tomo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomo.model.TokenBase;
import com.tomo.model.dto.TokenInfoDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TokenInfoMapper extends BaseMapper<TokenInfoDTO> {
    @Select({
        """
        <script>
            select * from token_info
            where
            <foreach collection="tokenBase" item="item" separator=" OR ">
                (
                    chain_id = #{item.chainId}
                     <if test="item.address != null and item.address != ''">
                           and address = #{item.address} 
                    </if>
                    <if test="item.address == null or item.address == ''">
                           and address is null
                    </if>
                )
            </foreach>
        </script>
        """
    })
    List<TokenInfoDTO> batchQuery(@Param("tokenBase") List<TokenBase> tokenBase);

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
              #{tokenInfoDTO.liquidityUsd},
              #{tokenInfoDTO.realPrice},
              #{tokenInfoDTO.volume24h},
              #{tokenInfoDTO.change24h},
              #{tokenInfoDTO.marketCap},
              #{tokenInfoDTO.fdvUsd},
              #{tokenInfoDTO.totalSupply},
              #{tokenInfoDTO.riskLevel}) 
             ON CONFLICT (chain_id, address) 
             DO UPDATE SET
              chain_id = COALESCE(#{tokenInfoDTO.chainId}, token_info.chain_id),
              address = COALESCE(#{tokenInfoDTO.address}, token_info.address),
              coingecko_coin_id = COALESCE(#{tokenInfoDTO.coingeckoCoinId}, token_info.coingecko_coin_id),
              coingecko_chain_id = COALESCE(#{tokenInfoDTO.coingeckoChainId}, token_info.coingecko_chain_id),
              is_native = COALESCE(#{tokenInfoDTO.isNative}, token_info.is_native),
              "name" = COALESCE(#{tokenInfoDTO.name}, token_info.name),
              display_name = COALESCE(#{tokenInfoDTO.displayName}, token_info.display_name),
              symbol = COALESCE(#{tokenInfoDTO.symbol}, token_info.symbol),
              image_url = COALESCE(#{tokenInfoDTO.imageUrl}, token_info.image_url),
              decimals = COALESCE(#{tokenInfoDTO.decimals}, token_info.decimals),
              website_url = COALESCE(#{tokenInfoDTO.websiteUrl}, token_info.website_url),
              twitter_url = COALESCE(#{tokenInfoDTO.twitterUrl}, token_info.twitter_url),
              telegram_url = COALESCE(#{tokenInfoDTO.telegramUrl}, token_info.telegram_url),
              pool_address = COALESCE(#{tokenInfoDTO.poolAddress}, token_info.pool_address),
              is_pool_base_token = COALESCE(#{tokenInfoDTO.isPoolBaseToken}, token_info.is_pool_base_token),
              liquidity_usd = COALESCE(#{tokenInfoDTO.liquidityUsd}, token_info.liquidity_usd),
              real_price = COALESCE(#{tokenInfoDTO.realPrice}, token_info.real_price),
              volume24h = COALESCE(#{tokenInfoDTO.volume24h}, token_info.volume24h),
              change24h = COALESCE(#{tokenInfoDTO.change24h}, token_info.change24h),
              market_cap = COALESCE(#{tokenInfoDTO.marketCap}, token_info.market_cap),
              fdv_usd = COALESCE(#{tokenInfoDTO.fdvUsd}, token_info.fdv_usd),
              total_supply = COALESCE(#{tokenInfoDTO.totalSupply}, token_info.total_supply),
              risk_level = COALESCE(#{tokenInfoDTO.riskLevel}, token_info.risk_level)
            """
    })
    boolean insertOrUpdate(@Param("tokenInfoDTO") TokenInfoDTO tokenInfoDTO);

    @Insert({
            """
            <script>
             INSERT INTO token_info (
                chain_id, address, coingecko_coin_id,coingecko_chain_id, is_native, name, display_name, symbol, image_url, decimals, website_url, twitter_url, telegram_url,pool_address,is_pool_base_token,liquidity_usd, real_price, volume24h, change24h, market_cap, fdv_usd,total_supply,risk_level
             ) VALUES
              <foreach collection="tokenInfoDTOs" item="tokenInfoDTO" separator=",">
                     ( #{tokenInfoDTO.chainId},
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
                      #{tokenInfoDTO.liquidityUsd},
                      #{tokenInfoDTO.realPrice},
                      #{tokenInfoDTO.volume24h},
                      #{tokenInfoDTO.change24h},
                      #{tokenInfoDTO.marketCap},
                      #{tokenInfoDTO.fdvUsd},
                      #{tokenInfoDTO.totalSupply},
                      #{tokenInfoDTO.riskLevel}) 
              </foreach>
             ON CONFLICT (chain_id, address) 
             DO UPDATE SET
              chain_id = COALESCE(EXCLUDED.chain_id, token_info.chain_id),
              address = COALESCE(EXCLUDED.address, token_info.address),
              coingecko_coin_id = COALESCE(EXCLUDED.coingecko_coin_id, token_info.coingecko_coin_id),
              coingecko_chain_id = COALESCE(EXCLUDED.coingecko_chain_id, token_info.coingecko_chain_id),
              is_native = COALESCE(EXCLUDED.is_native, token_info.is_native),
              "name" = COALESCE(EXCLUDED.name, token_info.name),
              display_name = COALESCE(EXCLUDED.display_name, token_info.display_name),
              symbol = COALESCE(EXCLUDED.symbol, token_info.symbol),
              image_url = COALESCE(EXCLUDED.display_name, token_info.image_url),
              decimals = COALESCE(EXCLUDED.decimals, token_info.decimals),
              website_url = COALESCE(EXCLUDED.website_url, token_info.website_url),
              twitter_url = COALESCE(EXCLUDED.twitter_url, token_info.twitter_url),
              telegram_url = COALESCE(EXCLUDED.telegram_url, token_info.telegram_url),
              pool_address = COALESCE(EXCLUDED.pool_address, token_info.pool_address),
              is_pool_base_token = COALESCE(EXCLUDED.is_pool_base_token, token_info.is_pool_base_token),
              liquidity_usd = COALESCE(EXCLUDED.liquidity_usd, token_info.liquidity_usd),
              real_price = COALESCE(EXCLUDED.real_price, token_info.real_price),
              volume24h = COALESCE(EXCLUDED.volume24h, token_info.volume24h),
              change24h = COALESCE(EXCLUDED.change24h, token_info.change24h),
              market_cap = COALESCE(EXCLUDED.market_cap, token_info.market_cap),
              fdv_usd = COALESCE(EXCLUDED.fdv_usd, token_info.fdv_usd),
              total_supply = COALESCE(EXCLUDED.total_supply, token_info.total_supply),
              risk_level = COALESCE(EXCLUDED.risk_level, token_info.risk_level)
              </script>
            """
    })
    boolean batchInsertOrUpdate(List<TokenInfoDTO> tokenInfoDTOs);
}