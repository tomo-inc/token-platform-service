package com.tomo.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@TableName(value = "token_price")
public class TokenPriceDTO {
    // 禁止自增，不能为空，需要和币种id保持一致
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "coin_id")
    private String coinId;

    @TableField(value = "chain_id")
    private Long chainId;

    @TableField(value = "coingecko_chain_id")
    private String coingeckoChainId;

    @TableField(value = "address")
    private String address;

    @TableField(value = "pool_address")
    private String poolAddress;

    @TableField(value = "is_pool_base_token")
    private Boolean isPoolBaseToken;

    @TableField(value = "liquidity_usd")
    private BigDecimal liquidityUsd;

    @TableField(value = "real_price")
    private BigDecimal realPrice;

    @TableField(value = "volume24h")
    private BigDecimal volume24h;

    @TableField(value = "change24h")
    private BigDecimal change24h;

    @TableField(value = "market_cap")
    private BigDecimal marketCap;

    @TableField(value = "fdv_usd")
    private BigDecimal fdvUsd;

    @TableField(value = "total_supply")
    private BigDecimal totalSupply;

    @TableField(value = "update_time")
    private Timestamp updateTime;

    @TableField(value = "create_time")
    private Timestamp createTime;
}