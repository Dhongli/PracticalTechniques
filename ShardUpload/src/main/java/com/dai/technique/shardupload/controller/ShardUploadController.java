package com.dai.technique.shardupload.controller;

import com.dai.technique.shardupload.comm.Result;
import com.dai.technique.shardupload.comm.ResultUtils;
import com.dai.technique.shardupload.dto.ShardUploadCompleteRequest;
import com.dai.technique.shardupload.dto.ShardUploadDetailResponse;
import com.dai.technique.shardupload.dto.ShardUploadInitRequest;
import com.dai.technique.shardupload.dto.ShardUploadPartRequest;
import com.dai.technique.shardupload.service.ShardUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/3/26 21:02 <br>
 * <b>author</b>：ready likun_557@163.com
 */
@RestController
@RequestMapping("/shardUpload")
public class ShardUploadController {

    private final ShardUploadService shardUploadService;

    @Autowired
    public ShardUploadController(ShardUploadService shardUploadService) {
        this.shardUploadService = shardUploadService;
    }

    /**
     * 创建分片上传任务
     *
     * @return 分片任务id
     */
    @PostMapping("/init")
    public Result<String> init(@RequestBody ShardUploadInitRequest request) {
        String shardUploadId = this.shardUploadService.init(request);
        return ResultUtils.ok(shardUploadId);
    }

    /**
     * 上传分片（客户端需遍历上传分片文件）
     *
     * @return
     */
    @PostMapping("/uploadPart")
    public Result<Boolean> uploadPart(ShardUploadPartRequest request) throws IOException {
        this.shardUploadService.uploadPart(request);
        return ResultUtils.ok(true);
    }

    /**
     * 合并分片，完成上传
     *
     * @return
     */
    @PostMapping("/complete")
    public Result<Boolean> complete(@RequestBody ShardUploadCompleteRequest request) throws IOException {
        this.shardUploadService.complete(request);
        return ResultUtils.ok(true);
    }

    /**
     * 获取分片任务详细信息
     *
     * @param shardUploadId 分片任务id
     * @return
     */
    @GetMapping("/detail")
    public Result<ShardUploadDetailResponse> detail(@RequestParam("shardUploadId") String shardUploadId) {
        return ResultUtils.ok(this.shardUploadService.detail(shardUploadId));
    }
}
