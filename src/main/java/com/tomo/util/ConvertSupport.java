package com.tomo.util;

import jakarta.annotation.Nullable;
import org.mapstruct.Named;

import java.util.Objects;

@Named("convertSupport")
public class ConvertSupport {

    @Named("upper")
    public @Nullable String upper(String str) {
        return Objects.isNull(str) ? null : str.toUpperCase();
    }

    @Named("lower")
    public @Nullable String lower(String str) {
        return Objects.isNull(str) ? null : str.toLowerCase();
    }

    /**
     * 不做处理，直接赋值，用于跳过某些深拷贝
     */
    @Named("direct")
    public @Nullable <T> T direct(T t) {
        return t;
    }
}
