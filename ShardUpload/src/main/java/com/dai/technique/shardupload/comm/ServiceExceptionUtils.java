package com.dai.technique.shardupload.comm;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/3/26 22:47 <br>
 * <b>author</b>：ready likun_557@163.com
 */
public class ServiceExceptionUtils {
    public static ServiceException exception(String message, String code) {
        return new ServiceException(message, code);
    }

    public static ServiceException exception(String message) {
        return exception(message, null);
    }

    public static void throwException(String message, String code) {
        throw exception(message, code);
    }

    public static void throwException(String message) {
        throwException(message, null);
    }

}
