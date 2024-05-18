package com.dai.technique.shardupload.comm;

/**
 * <b>description</b>： 异常处理 <br>
 * <b>time</b>：2024/3/26 22:46 <br>
 * <b>author</b>：ready likun_557@163.com
 */
public class ServiceException extends RuntimeException{
    private String code;

    public ServiceException(String message, String code) {
        super(message);
        this.code = code;
    }
}
