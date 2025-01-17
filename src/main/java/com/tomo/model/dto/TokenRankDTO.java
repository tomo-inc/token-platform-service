package com.tomo.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "token_rank")
public class TokenRankDTO {
    //info
    @TableField(value = "chain_id")
    private Long chainId;

    @TableField(value = "address")
    private String address;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "symbol")
    private String symbol;

    @TableField(value = "image_url")
    private String imageUrl;

    @TableField(value = "source")
    private String source;

    @TableField(value = "tag")
    private String tag;

    // price
    @TableField(value = "real_price")
    private String realPrice;

    @TableField(value = "volume24h")
    private String volume24h;

    @TableField(value = "change24h")
    private String change24h;

    @TableField(value = "market_cap")
    private String marketCap;

    // time
    @TableField(value = "update_time")
    private Long updateTime;

    @TableField(value = "create_time")
    private Long createTime;
}
