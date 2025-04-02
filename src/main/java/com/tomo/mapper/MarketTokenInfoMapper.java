package com.tomo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.model.req.MarketTokenQueryReq;
import com.tomo.model.req.MarketTokenReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MarketTokenInfoMapper extends BaseMapper<MarketTokenInfo> {

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
                    t.total_supply totalSupply,
                    t.risk_level as riskLevel
                FROM market_token_info t
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
    IPage<MarketTokenInfo> pageMarketToken(@Param("req") MarketTokenQueryReq req, Page pg);

    List<MarketTokenInfo> queryTokenList(@Param("list") List<MarketTokenReq> list);
    List<MarketTokenInfo> queryByCoinIds(@Param("list") List<String> list);

    void batchInsert(List<MarketTokenInfo> tokenInfoList);

    List<MarketTokenInfo> querySocialNull();

    int updateById(MarketTokenInfo tokenInfo);

    @Select("""
        select t.id,t.address,t.chain_index FROM market_token_info t
        where chain_index = #{chainIndex} and address is not null and (force_safe IS NULL OR force_safe <> 1) and (risk != 4 or risk is null)
    """)
    List<MarketTokenInfo> getSecurityList(Long chainIndex);

    @Select("""
        select t.id,t.address,t.chain_index FROM market_token_info t
        where chain_index = #{chainIndex} and address is not null and risk = 0 and (force_safe IS NULL OR force_safe <> 1)
        limit 500
    """)
    List<MarketTokenInfo> getNullSecurityList(Long chainIndex);
}