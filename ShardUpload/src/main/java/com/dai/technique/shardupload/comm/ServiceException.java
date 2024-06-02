package com.dai.technique.shardupload.comm;

/**
 * @Description: 异常处理
 * @date 2024-06-02 9:15
 */
public class ServiceException extends RuntimeException{
    private String code;

    public ServiceException(String message, String code) {
        super(message);
        this.code = code;
    }
}
