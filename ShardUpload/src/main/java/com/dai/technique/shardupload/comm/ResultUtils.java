package com.dai.technique.shardupload.comm;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/3/26 21:09 <br>
 * <b>author</b>：ready likun_557@163.com
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
