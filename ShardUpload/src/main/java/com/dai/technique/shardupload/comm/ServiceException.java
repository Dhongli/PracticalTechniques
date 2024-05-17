package com.dai.technique.shardupload.comm;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/3/26 22:46 <br>
 * <b>author</b>：ready likun_557@163.com
 */
public class ServiceException extends  RuntimeException{
    private String code;

    public ServiceException(String message, String code) {
        super(message);
        this.code = code;
    }
}
