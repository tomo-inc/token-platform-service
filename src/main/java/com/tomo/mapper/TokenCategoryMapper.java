package com.tomo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomo.model.dto.TokenCategoryCoinGeckoDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TokenCategoryMapper extends BaseMapper<TokenCategoryCoinGeckoDTO> {

    @Insert({
        """
            INSERT INTO token_category (
                chain_id, address, coingecko_coin_id,coingecko_chain_id, is_native, name, display_name, symbol, image_url, decimals, website_url, twitter_url, telegram_url
            ) VALUES (
                #{tokenCategoryCoinGeckoDTO.chainId},
                #{tokenCategoryCoinGeckoDTO.address},
                #{tokenCategoryCoinGeckoDTO.coingeckoCoinId},
                #{tokenCategoryCoinGeckoDTO.coingeckoChainId},
                #{tokenCategoryCoinGeckoDTO.isNative},
                #{tokenCategoryCoinGeckoDTO.name},
                #{tokenCategoryCoinGeckoDTO.displayName},
                #{tokenCategoryCoinGeckoDTO.symbol},
                #{tokenCategoryCoinGeckoDTO.imageUrl},
                #{tokenCategoryCoinGeckoDTO.decimals},
                #{tokenCategoryCoinGeckoDTO.websiteUrl},
                #{tokenCategoryCoinGeckoDTO.twitterUrl},
                #{tokenCategoryCoinGeckoDTO.telegramUrl}) ON DUPLICATE KEY UPDATE
           chain_id = IF(#{tokenCategoryCoinGeckoDTO.chainId} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.chainId}, chain_id),
           address = IF(#{tokenCategoryCoinGeckoDTO.address} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.address}, address),
           coingecko_coin_id = IF(#{tokenCategoryCoinGeckoDTO.coingeckoCoinId} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.coingeckoCoinId}, coingecko_coin_id),
           coingecko_chain_id = IF(#{tokenCategoryCoinGeckoDTO.coingeckoChainId} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.coingeckoChainId}, coingecko_chain_id),
           is_native = IF(#{tokenCategoryCoinGeckoDTO.isNative} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.isNative}, is_native),
           name = IF(#{tokenCategoryCoinGeckoDTO.name} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.name}, name),
           display_name = IF(#{tokenCategoryCoinGeckoDTO.displayName} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.displayName}, display_name),
           symbol = IF(#{tokenCategoryCoinGeckoDTO.symbol} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.symbol}, symbol),
           image_url = IF(#{tokenCategoryCoinGeckoDTO.imageUrl} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.imageUrl}, image_url),
           decimals = IF(#{tokenCategoryCoinGeckoDTO.decimals} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.decimals}, decimals),
           website_url = IF(#{tokenCategoryCoinGeckoDTO.websiteUrl} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.websiteUrl}, website_url),
           twitter_url = IF(#{tokenCategoryCoinGeckoDTO.twitterUrl} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.twitterUrl}, twitter_url),
           telegram_url = IF(#{tokenCategoryCoinGeckoDTO.telegramUrl} IS NOT NULL, #{tokenCategoryCoinGeckoDTO.telegramUrl}, telegram_url)
        """
    })
    boolean insertOrUpdate(@Param("tokenCategoryCoinGeckoDTO") TokenCategoryCoinGeckoDTO tokenCategoryCoinGeckoDTO);

}