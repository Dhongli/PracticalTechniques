package com.dai.technique.shardupload.dto;

import lombok.Data;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/3/26 22:08 <br>
 * <b>author</b>：ready likun_557@163.com
 */
@Data
public class ShardUploadCompleteRequest {
    /**
     * 分片上传任务id（由初始化分片接口返回的）
     */
    private String shardUploadId;
}
