package com.tomo.model;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum IntervalEnum {
    HOUR("hour"),
    FOUR_HOUR("four_hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    MINUTE("minute"),
    FIVE_MINUTE("five_minute"),
    FIFTEEN_MINUTE("fifteen_minute");

    @EnumValue
    private final String value;

    IntervalEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static IntervalEnum fromValue(String value) {
        for (IntervalEnum e : IntervalEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
