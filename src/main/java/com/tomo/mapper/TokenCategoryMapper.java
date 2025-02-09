package com.tomo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomo.model.dto.TokenCategoryCoinGeckoDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
                    #{tokenCategoryCoinGeckoDTO.telegramUrl})
               ON CONFLICT (chain_id, address) 
               DO UPDATE SET
                   chain_id = COALESCE(#{tokenCategoryCoinGeckoDTO.chainId}, token_category.chain_id),
                   address = COALESCE(#{tokenCategoryCoinGeckoDTO.address}, token_category.address),
                   coingecko_coin_id = COALESCE(#{tokenCategoryCoinGeckoDTO.coingeckoCoinId}, token_category.coingecko_coin_id),
                   coingecko_chain_id = COALESCE(#{tokenCategoryCoinGeckoDTO.coingeckoChainId}, token_category.coingecko_chain_id),
                   is_native = COALESCE(#{tokenCategoryCoinGeckoDTO.isNative}, token_category.is_native),
                   name = COALESCE(#{tokenCategoryCoinGeckoDTO.name}, token_category.name),
                   display_name = COALESCE(#{tokenCategoryCoinGeckoDTO.displayName}, token_category.display_name),
                   symbol = COALESCE(#{tokenCategoryCoinGeckoDTO.symbol}, token_category.symbol),
                   image_url = COALESCE(#{tokenCategoryCoinGeckoDTO.imageUrl}, token_category.image_url),
                   decimals = COALESCE(#{tokenCategoryCoinGeckoDTO.decimals}, token_category.decimals),
                   website_url = COALESCE(#{tokenCategoryCoinGeckoDTO.websiteUrl}, token_category.website_url),
                   twitter_url = COALESCE(#{tokenCategoryCoinGeckoDTO.twitterUrl}, token_category.twitter_url),
                   telegram_url = COALESCE(#{tokenCategoryCoinGeckoDTO.telegramUrl}, token_category.telegram_url)
            """
    })
    boolean insertOrUpdate(@Param("tokenCategoryCoinGeckoDTO") TokenCategoryCoinGeckoDTO tokenCategoryCoinGeckoDTO);

    @Insert({
            """
                <script>
                INSERT INTO token_category (
                    chain_id, address, coingecko_coin_id,coingecko_chain_id, is_native, name, display_name, symbol, image_url, decimals, website_url, twitter_url, telegram_url
                ) VALUES 
                 <foreach collection="tokenCategoryUpdateList" item="tokenCategoryCoinGeckoDTO" separator=",">
                    (#{tokenCategoryCoinGeckoDTO.chainId},
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
                    #{tokenCategoryCoinGeckoDTO.telegramUrl})
                    </foreach>
               ON CONFLICT (chain_id, address) 
               DO UPDATE SET
                   chain_id = COALESCE(EXCLUDED.chain_id, token_category.chain_id),
                   address = COALESCE(EXCLUDED.address, token_category.address),
                   coingecko_coin_id = COALESCE(EXCLUDED.coingecko_coin_id, token_category.coingecko_coin_id),
                   coingecko_chain_id = COALESCE(EXCLUDED.coingecko_chain_id, token_category.coingecko_chain_id),
                   is_native = COALESCE(EXCLUDED.is_native, token_category.is_native),
                   name = COALESCE(EXCLUDED.name, token_category.name),
                   display_name = COALESCE(EXCLUDED.display_name, token_category.display_name),
                   symbol = COALESCE(EXCLUDED.symbol, token_category.symbol),
                   image_url = COALESCE(EXCLUDED.image_url, token_category.image_url),
                   decimals = COALESCE(EXCLUDED.decimals, token_category.decimals),
                   website_url = COALESCE(EXCLUDED.website_url, token_category.website_url),
                   twitter_url = COALESCE(EXCLUDED.twitter_url, token_category.twitter_url),
                   telegram_url = COALESCE(EXCLUDED.telegram_url, token_category.telegram_url)
            </script>
            """
    })
    void batchInsertOrUpdate(List<TokenCategoryCoinGeckoDTO> tokenCategoryUpdateList);
}