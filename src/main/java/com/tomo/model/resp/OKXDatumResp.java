package com.tomo.model.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OKXDatumResp {
    List<Market> marketListsTokenInfos;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Market {
        private String chainId;
        private String change1H;
        private String change24H;
        private String change4H;
        private String collectionToken;
        private String marketCap;
        private String price;
        private String riskLevel;
        private String tokenContractAddress;
        private String tokenLogoUrl;
        private String tokenSymbol;
        private String txs;
        private String volume;
    }
}
