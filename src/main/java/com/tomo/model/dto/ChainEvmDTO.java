package com.tomo.model.dto;

import com.tomo.model.ChainPlatformType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChainEvmDTO {
    private Integer chainId;
    private String name;
    private String nativeCurrencyName;
    private String nativeCurrencySymbol;
    private Integer nativeCurrencyDecimals;
    private String[] rpcUrls;
    private String blockExplorerUrl;
    private ChainPlatformType platformType;
    private Boolean isTestnet;
    private String icon;
}
