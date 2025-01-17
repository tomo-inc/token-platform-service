package com.tomo.model.resp;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class SolanaSecurityResp {
    private int code;
    private String message;
    private Map<String, SolanaTokenDetails> result;

    @Data
    public static class SolanaTokenDetails {
        private Authority balance_mutable_authority;
        private Authority closable;
        private List<Creator> creators;
        private String default_account_state;
        private Authority default_account_state_upgradable;
        private List<Object> dex;
        private Authority freezable;
        private List<Object> holders;
        private List<Object> lp_holders;
        private Object metadata;
        private Object metadata_mutable;
        private Authority mintable;
        private String non_transferable;
        private String total_supply;
        private TransferFee transfer_fee;
        private Authority transfer_fee_upgradable;
        private List<Object> transfer_hook;
        private Authority transfer_hook_upgradable;
        private int trusted_token;
    }

    @Data
    public static class TransferFee {

        private FeeRate current_fee_rate;
        private List<ScheduledFeeRate> scheduled_fee_rate;

        @Data
        public static class FeeRate {
            private BigDecimal fee_rate;
            private BigDecimal maximum_fee;
        }

        @Data
        public static class ScheduledFeeRate {
            private String epoch;
            private BigDecimal fee_rate;
            private BigDecimal maximum_fee;
        }
    }

    @Data
    public static class Creator{
        private String address;
        private String malicious_address;
    }

    @Data
    public static class Authority {
        private List<Object> authority;
        private String status;
    }
}