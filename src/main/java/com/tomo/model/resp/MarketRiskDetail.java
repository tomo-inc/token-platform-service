package com.tomo.model.resp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MarketRiskDetail {
    private Risk risk;
    private Warn warn;

    @Data
    public static class Risk {
        private Boolean ownerChangeBalance;
        private Boolean externalCall;
        private Boolean hiddenOwner;
        private Boolean isProxy;
        private Boolean isOpenSource;
        private Boolean balanceMutableAuthority;
        private Boolean transferHook;
        private Boolean maliciousAddress;
        private Boolean isHoneypot;
        private Boolean buyTax;
        private Boolean sellTax;
        private Boolean nonTransferable;
        private Boolean feeRate;

    }

    @Data
    public static class Warn {
        private BigDecimal buyTax;
        private BigDecimal sellTax;
        private Boolean cannotSellAll;
        private Boolean slippageModifiable;
        private Boolean personalSlippageModifiable;
        private BigDecimal feeRate;
        private Boolean transferFeeUpgradable;
        private Boolean transferPausable;
        private Boolean tradingCooldown;
        private Boolean isBlacklisted;
        private Boolean isWhitelisted;
        private Boolean isClosable;
        private Boolean freezable;
        private Boolean transferHookUpgradable;

    }

}
