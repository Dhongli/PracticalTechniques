package com.dai.technique.shardupload.comm;

/**
 * <b>description</b>： 异常处理类 <br>
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
