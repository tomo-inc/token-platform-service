package com.tomo.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "token_risk_goplus")
public class TokenRiskGoplusDTO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "chain_id")
    private Long chainId;

    @TableField(value = "address")
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
    private Boolean gasAbuse; // 1
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
    private String dex;
    private Boolean isAntiWhale;
    private Boolean antiWhaleModifiable;
    private Boolean tradingCooldown;
    private Boolean personalSlippageModifiable;
    private Integer holderCount;
    private String totalSupply;
    private String holders;
    private String ownerBalance;
    private String ownerPercent;
    private String creatorAddress;
    private String creatorBalance;
    private String creatorPercent;
    private Integer lpHolderCount;
    private String lpTotalSupply;
    private String lpHolders;
    private Boolean isTrueToken;
    private Boolean isAirdropScam;
    private Boolean trustList;
    private String otherPotentialRisks;
    private String note;
    private String fakeToken;
    private String metadata; //json
    private Integer defaultAccountState;
    private Boolean nonTransferable;
    private String creator; // json
    private String transferFee;
    private String transferHook;
    private Boolean trustedToken;
    private String metadataMutable;
    private String mintable;
    private String freezable;
    private String closable;
    private String defaultAccountStateUpgradable;
    private String balanceMutableAuthority;
    private String transferFeeUpgradable;
    private String transferHookUpgradable;

    private int riskLevel;
}