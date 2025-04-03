package com.tomo.model.market;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketTokenSecurityInfo {
    private Long id;
    private Long chainIndex;
    private String address;
    private Boolean isOpenSource;
    private Boolean isProxy;
    private Boolean isMintable;
    private String ownerAddress;
    private Boolean canTakeBackOwnership;
    private Boolean ownerChangeBalance;
    private Boolean hiddenOwner;
    private Boolean selfdestruct;
    private Boolean externalCall;
    private Boolean gasAbuse;
    private Boolean isInDex;
    private BigDecimal buyTax;
    private BigDecimal sellTax;
    private Boolean cannotBuy;
    private Boolean cannotSellAll;
    private Boolean slippageModifiable;
    private Boolean isHoneypot;
    private Boolean transferPausable;
    private Boolean isBlacklisted;
    private Boolean isWhitelisted;
//    private Map<String, Object> dex;
    private String dex;
    private Boolean isAntiWhale;
    private Boolean antiWhaleModifiable;
    private Boolean tradingCooldown;
    private Boolean personalSlippageModifiable;
    private String tokenName;
    private String tokenSymbol;
    private Integer holderCount;
    private String totalSupply;
//    private Map<String, Object> holders;
    private String holders;
    private BigDecimal ownerBalance;
    private BigDecimal ownerPercent;
    private String creatorAddress;
    private BigDecimal creatorBalance;
    private BigDecimal creatorPercent;
    private Integer lpHolderCount;
    private BigDecimal lpTotalSupply;
//    private Map<String, Object> lpHolders;
    private String lpHolders;
    private Boolean isTrueToken;
    private Boolean isAirdropScam;
    private Boolean trustList;
    private String otherPotentialRisks;
    private String note;
//    private Map<String, Object> fakeToken;
    private String fakeToken;
//    private Map<String, Object> metadata;
    private String metadata;
    private Integer defaultAccountState;
    private Boolean nonTransferable;
//    private Map<String, Object> creator;
    private String creator;
//    private Map<String, Object> transferFee;
    private String transferFee;
//    private Map<String, Object> transferHook;
    private String transferHook;
    private Boolean trustedToken;
//    private Map<String, Object> metadataMutable;
    private String metadataMutable;
//    private Map<String, Object> mintable;
    private String mintable;
//    private Map<String, Object> freezable;
    private String freezable;
//    private Map<String, Object> closable;
    private String closable;
//    private Map<String, Object> defaultAccountStateUpgradable;
    private String defaultAccountStateUpgradable;
//    private Map<String, Object> balanceMutableAuthority;
    private String balanceMutableAuthority;
//    private Map<String, Object> transferFeeUpgradable;
    private String transferFeeUpgradable;
//    private Map<String, Object> transferHookUpgradable;
    private String transferHookUpgradable;
    private Timestamp createdTime;
    private Timestamp updatedTime;
}