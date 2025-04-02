package com.tomo.model.market;

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
    private Map<String, Object> dex;
    private Boolean isAntiWhale;
    private Boolean antiWhaleModifiable;
    private Boolean tradingCooldown;
    private Boolean personalSlippageModifiable;
    private String tokenName;
    private String tokenSymbol;
    private Integer holderCount;
    private String totalSupply;
    private Map<String, Object> holders;
    private BigDecimal ownerBalance;
    private BigDecimal ownerPercent;
    private String creatorAddress;
    private BigDecimal creatorBalance;
    private BigDecimal creatorPercent;
    private Integer lpHolderCount;
    private BigDecimal lpTotalSupply;
    private Map<String, Object> lpHolders;
    private Boolean isTrueToken;
    private Boolean isAirdropScam;
    private Boolean trustList;
    private String otherPotentialRisks;
    private String note;
    private Map<String, Object> fakeToken;
    private Map<String, Object> metadata;
    private Integer defaultAccountState;
    private Boolean nonTransferable;
    private Map<String, Object> creator;
    private Map<String, Object> transferFee;
    private Map<String, Object> transferHook;
    private Boolean trustedToken;
    private Map<String, Object> metadataMutable;
    private Map<String, Object> mintable;
    private Map<String, Object> freezable;
    private Map<String, Object> closable;
    private Map<String, Object> defaultAccountStateUpgradable;
    private Map<String, Object> balanceMutableAuthority;
    private Map<String, Object> transferFeeUpgradable;
    private Map<String, Object> transferHookUpgradable;
    private Timestamp createdTime;
    private Timestamp updatedTime;
}