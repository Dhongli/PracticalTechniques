package com.dai.technique.shardupload.comm;

/**
 * 异常处理工具类
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
