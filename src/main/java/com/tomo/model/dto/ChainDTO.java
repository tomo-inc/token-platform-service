package com.tomo.model.dto;

import com.tomo.model.ChainPlatformType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChainDTO {

    private String name;
    private String coinGeckoId;
    private String coinGeckoDexId;
    private String coinGeckoNativeCoinId;
    private String ankrId;
    private String rangoId;
    private String okxId;
    private ChainPlatformType platformType;
    private Integer chainId;
    private String rpcUrl;
    private String[] rpcUrls;
    private Boolean supportNoFeeSwap;
    private BigDecimal loan;
    private Boolean isTestnet;
    private String blockExplorerName;
    private String blockExplorerUrl;
    private String blockExplorerApiUrl;
    private String icon;
}