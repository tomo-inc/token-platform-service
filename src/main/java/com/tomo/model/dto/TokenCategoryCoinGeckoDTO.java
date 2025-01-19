package com.tomo.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName(value = "token_category")
public class TokenCategoryCoinGeckoDTO implements Cloneable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long coinId;

    @TableField(value = "chain_id")
    private Long chainId;

    @TableField(value = "address")
    private String address;

    @TableField(value = "coingecko_coin_id")
    private String coingeckoCoinId;

    @TableField(value = "coingecko_chain_id")
    private String coingeckoChainId;

    @TableField(value = "coingecko_network")
    private String coingeckoNetwork;

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

    // url

    @TableField(value = "website_url")
    private String websiteUrl;

    @TableField(value = "twitter_url")
    private String twitterUrl;

    @TableField(value = "telegram_url")
    private String telegramUrl;

    @TableField(value = "update_time")
    private Timestamp updateTime;

    @TableField(value = "create_time")
    private Timestamp createTime;

    @Override
    public Object clone() {
        try{
            return super.clone();
        }catch (CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
    }

}