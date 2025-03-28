package com.tomo.model.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 市场代币查询DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MarketTokenQueryReq extends PageReq{
    
    /**
     * 链索引
     */
    private Integer chainIndex;
    
    /**
     * 合约地址
     */
    private String address;
    
    /**
     * 代币符号
     */
    private String symbol;
} 