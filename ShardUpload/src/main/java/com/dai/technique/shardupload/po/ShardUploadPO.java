package com.dai.technique.shardupload.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/3/26 21:32 <br>
 * <b>author</b>：ready likun_557@163.com
 */
@Data
@TableName("t_shard_upload")
public class ShardUploadPO {
    private String id;

    /**
     * 文件名称
     */

    private String fileName;

    /**
     * 分片数量
     */
    private Integer partNum;

    /**
     * 文件md5字
     */
    private String md5;

    /**
     * 文件最终存储完整路径
     */
    private String fileFullPath;
}
