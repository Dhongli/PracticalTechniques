package com.dai.technique.shardupload.comm;

import lombok.Data;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/3/26 21:06 <br>
 * <b>author</b>：ready likun_557@163.com
 */
@Data
public class Result<T> {
    /**
     * 编码,1：成功，其他值失败
     */
    private String code;
    /**
     * 结果
     */
    public T data;
    /**
     * 提示消息
     */
    private String msg;
}
