package com.tomo.model.market.enums;

import lombok.Getter;

@Getter
public enum RiskLevel {
    NONE(0, "none"),
    SAFE(1, "safe"),
    WARN(2, "warn"),
    RISK(3, "risk"),
    BLACK(4, "black"),
    ;

    private Integer code;
    private String value;

    RiskLevel(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

}
