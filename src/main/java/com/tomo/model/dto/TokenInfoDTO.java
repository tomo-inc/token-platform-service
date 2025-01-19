package com.tomo.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "token_info")
public class TokenInfoDTO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "chain_id")
    private Long chainId;

    @TableField(value = "address")
    private String address;

    @TableField(value = "coingecko_coin_id")
    private String coingeckoCoinId;

    @TableField(value = "coingecko_chain_id")
    private String coingeckoChainId;

    @TableField(value = "is_native")
    private Boolean isNative;

    @TableField(value = "name")
    private String name;

    @TableField(value = "display_name")
    private String displayName;

    @TableField(value = "symbol")
    private String symbol;

    @TableField(value = "image_url")
    private String imageUrl;

    @TableField(value = "decimals")
    private Integer decimals;

    @TableField(value = "website_url")
    private String websiteUrl;

    @TableField(value = "twitter_url")
    private String twitterUrl;

    @TableField(value = "telegram_url")
    private String telegramUrl;

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

    @TableField(value = "risk_level")
    private int riskLevel;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "create_time")
    private Date createTime;

    public String getId(){
        return address+chainId;
    }
}