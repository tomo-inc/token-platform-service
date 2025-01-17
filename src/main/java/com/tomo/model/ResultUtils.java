package com.tomo.model;


import com.tomo.model.resp.Result;

import java.util.Map;

public class ResultUtils {
    public ResultUtils() {
    }

    public static boolean isSuccess(String code) {
        return code.equalsIgnoreCase("00000");
    }

    public static <T> Result<T> success(final T data) {
        return build("0", "success", data);
    }

    public static Result failure(final String msg) {
        return failure("-1", msg);
    }



    public static Result failure(final String code, final String msg) {
        return build(code, msg, (Object)null);
    }

    public static Result failure(final String code, final String msg, Map data) {
        return build(code, msg, data);
    }

    public static <T> Result<T> build(final String code, final String msg, final T data) {
        return new Result(code, msg, System.currentTimeMillis(), data);
    }
}
