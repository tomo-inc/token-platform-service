package com.tomo.model.dto;

import com.tomo.model.market.SocialInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 市场代币数据传输对象
 * 用于传输和展示市场上代币的详细信息，包括基本信息、社交信息、价格和流动性数据等
 */
@Data
public class MarketTokenDTO {

    /**
     * 链索引，代表该代币所在的区块链网络标识
     */
    private Long chainIndex;
    
    /**
     * 代币合约地址
     */
    private String address;
    
    /**
     * 是否为原生代币（如ETH、BNB等）
     */
    private Boolean isNative;
    
    /**
     * 代币名称
     */
    private String name;
    
    /**
     * 代币符号
     */
    private String symbol;
    
    /**
     * 代币图片URL
     */
    private String imageUrl;
    
    /**
     * 代币小数位数
     */
    private Integer decimals;
    
    /**
     * 代币社交信息，包括官网、社交媒体链接等
     */
    private SocialInfo social;
    
    /**
     * 风险等级，数值越高风险越大
     */
    private Integer riskLevel;
    
    /**
     * 是否强制标记为安全代币
     */
    private Boolean forceSafe;

    /**
     * 代币流动性池地址
     */
    private String poolAddress;
    
    /**
     * 是否为流动性池中的基础代币
     */
    private Boolean isPoolBaseToken;
    
    /**
     * 流动性池中美元价值
     */
    private BigDecimal liquidityUsd;
    
    /**
     * 代币实际价格
     */
    private BigDecimal realPrice;
    
    /**
     * 24小时交易量
     */
    private BigDecimal volume24h;
    
    /**
     * 24小时价格变化百分比
     */
    private BigDecimal change24h;
    
    /**
     * 市值（流通市值）
     */
    private BigDecimal marketCap;
    
    /**
     * 完全稀释估值（美元）
     */
    private BigDecimal fdvUsd;
    
    /**
     * 代币总供应量
     */
    private BigDecimal totalSupply;
}
