package com.dai.technique.shardupload.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/3/26 21:32 <br>
 * <b>author</b>：ready likun_557@163.com
 */
@Data
@TableName("t_shard_upload_part")
public class ShardUploadPartPO {
    private String id;

    /**
     * 分片任务id（t_shard_upload.id
     */
    private String shardUploadId;

    /**
     * 第几个分片，从1开始
     */
    private Integer partOrder;

    /**
     * 当前分片文件完整路径
     */
    private String fileFullPath;
}
