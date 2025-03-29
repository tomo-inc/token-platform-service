package com.tomo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tomo.model.dto.MarketTokenDTO;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.model.req.MarketTokenQueryReq;
import com.tomo.model.req.MarketTokenReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MarketTokenInfoMapper extends BaseMapper<MarketTokenInfo> {

    @Select({
            """
            <script>
                SELECT *
                FROM market_token_info
                WHERE chain_index = #{chainIndex} and address = #{address}
            </script>
            """
    })
    MarketTokenInfo selectByChainIndexAndAddress(@Param("chainIndex")Long chainIndex, @Param("address") String address);

    @Select({
            """
            <script>
                SELECT
                    t.id,
                    t.chain_index as chainIndex,
                    t.address,
                    t.is_native as isNative,
                    t.name,
                    t.symbol,
                    t.image_url as imageUrl,
                    t.decimals,
                    t.social,
                    t.risk_level as riskLevel,
                    t.force_safe as forceSafe,
                    p.pool_address as poolAddress,
                    p.is_pool_base_token as isPoolBaseToken,
                    p.liquidity_usd as liquidityUsd,
                    p.real_price as realPrice,
                    p.volume_24h as volume24h,
                    p.change_24h as change24h,
                    p.market_cap as marketCap,
                    p.fdv_usd as fdvUsd,
                    t.total_supply as totalSupply
                FROM market_token_info t
                LEFT JOIN market_token_price p ON t.id = p.token_id
                WHERE 1=1
                <if test="req.chainIndex != null">
                    AND t.chain_index = #{req.chainIndex}
                </if>
                <if test="req.address != null and req.address != ''">
                    AND t.address = #{req.address}
                </if>
                <if test="req.symbol != null and req.symbol != ''">
                    AND t.symbol = #{req.symbol}
                </if>
            </script>
            """
    })
    IPage<MarketTokenDTO> pageMarketToken(@Param("req") MarketTokenQueryReq req, Page pg);

    List<MarketTokenInfo> queryTokenList(@Param("list") List<MarketTokenReq> list);

//    MarketTokenInfo selectMarketTokenInfoById(long id);
//
//    MarketTokenInfo selectByChainIndexAndAddress(Long chainIndex, String address);
//
//    void insertMarketTokenInfo(MarketTokenInfo marketTokenInfo);
//
//    void updateMarketTokenInfo(MarketTokenInfo marketTokenInfo);
//
//    void deleteMarketTokenInfo(long id);
}