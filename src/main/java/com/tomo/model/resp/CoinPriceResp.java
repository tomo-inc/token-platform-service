package com.tomo.model.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tomo.model.ChainUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CoinPriceResp {
    private Long chainId;

    private String address;

    private Boolean isNative;

    private BigDecimal realPrice;

    private BigDecimal volume24h;

    private BigDecimal change24h;

}