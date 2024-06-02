package com.dai.technique.shardupload.comm;

/**
 * @Description: rest风格返回工具类
 * @date 2024-06-02 9:14
 */
public class ResultUtils {
    public static final String SUCCESS = "1";
    public static final String ERROR = "0";

    public static <T> Result<T> ok() {
        return result(SUCCESS, null, null);
    }

    public static <T> Result<T> ok(T data) {
        return result(SUCCESS, data, null);
    }

    public static <T> Result<T> error(String msg) {
        return result(ERROR, null, msg);
    }

    public static <T> Result<T> result(String code, T data, String msg) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }
}
