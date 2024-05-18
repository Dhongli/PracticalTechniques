package com.dai.technique.shardupload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dai.technique.shardupload.dto.ShardUploadCompleteRequest;
import com.dai.technique.shardupload.dto.ShardUploadDetailResponse;
import com.dai.technique.shardupload.dto.ShardUploadInitRequest;
import com.dai.technique.shardupload.dto.ShardUploadPartRequest;
import com.dai.technique.shardupload.po.ShardUploadPO;

import java.io.IOException;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>： 21:03 <br>
 * <b>author</b>：ready likun_557@163.com
 */
public interface ShardUploadService {
    /**
     * 创建分片上传任务
     *
     * @param request
     * @return 分片任务id
     */
    String init(ShardUploadInitRequest request);

    /**
     * 上传分片
     *
     * @param request
     */
    void uploadPart(ShardUploadPartRequest request) throws IOException;

    /**
     * 完成分片上传，合并分片文件
     *
     * @param request
     */
    void complete(ShardUploadCompleteRequest request) throws IOException;

    /**
     * 获取分片任务详细信息
     *
     * @param shardUploadId 分片任务id
     * @return
     */
    ShardUploadDetailResponse detail(String shardUploadId);
}
