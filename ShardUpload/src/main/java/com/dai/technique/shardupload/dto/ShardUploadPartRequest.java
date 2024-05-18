package com.dai.technique.shardupload.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * <b>description</b>：ShardUploadPart <br>
 * <b>time</b>：2024/3/26 22:12 <br>
 * <b>author</b>：ready likun_557@163.com
 */
@Data
public class ShardUploadPartRequest {
    /**
     * 分片上传任务id（由初始化分片接口返回的）
     */
    private String shardUploadId;
    /**
     * 第几个分片
     */
    private Integer partOrder;

    /**
     * 分片文件
     */
    private MultipartFile file;
}
