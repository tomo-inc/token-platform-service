package com.tomo.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NativeCoinSimpleInfoResp {
    private String id;
    private String name;
    @JsonProperty("native_coin_id")
    private String nativeCoinId;
}