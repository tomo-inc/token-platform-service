package com.tomo.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "four_meme_token")
public class FourMemeToken {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String tokenName;
    private String tokenAddress;
    private String tokenSymbol;
    private Integer tokenPrecision;
    private String imageUrl;
    private String raiseTokenSymbol;
    private String raiseTokenAddress;
    private String priceUsd;
    private String priceChangeH24;
    private String volumeH24;
    private String marketCapUsd;
    private Date createTime;
    private Date updateTime;
    private Long publishTime;
    private Double progress;
    private Boolean launchOnPancake;
}
