package com.tomo.model.resp;

import lombok.Data;

@Data
public class MarketRiskDetail {
    private Risk risk;
    private Warn warn;

    @Data
    public static class Risk {
        private boolean ownerChangeBalance;
        private boolean externalCall;
        private boolean hiddenOwner;
        private boolean isProxy;
        private boolean isOpenSource;
        private boolean balanceMutableAuthority;
        private boolean transferHook;
        private boolean maliciousAddress;
        private boolean isHoneypot;
        private boolean buyTax;
        private boolean sellTax;
        private boolean nonTransferable;
        private boolean feeRate;

    }

    @Data
    public static class Warn {
        private int buyTax;
        private int sellTax;
        private boolean cannotSellAll;
        private boolean slippageModifiable;
        private boolean personalSlippageModifiable;
        private int feeRate;
        private boolean transferFeeUpgradable;
        private boolean transferPausable;
        private boolean tradingCooldown;
        private boolean isBlacklisted;
        private boolean isWhitelisted;
        private boolean isClosable;
        private boolean freezable;
        private boolean transferHookUpgradable;

    }

}
