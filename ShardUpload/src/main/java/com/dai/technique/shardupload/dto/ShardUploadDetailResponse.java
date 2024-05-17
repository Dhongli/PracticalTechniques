package com.dai.technique.shardupload.dto;

import lombok.Data;

import java.util.List;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/3/27 8:11 <br>
 * <b>author</b>：ready likun_557@163.com
 */
@Data
public class ShardUploadDetailResponse {
    /**
     * 分片任务id
     */
    private String shardUploadId;
    /**
     * 分片数量
     */
    private Integer partNum;
    /**
     * 分片任务是否已上传完成
     */
    private Boolean success;
    /**
     * 已完成的分片任务编号列表
     */
    private List<Integer> partOrderList;
}
