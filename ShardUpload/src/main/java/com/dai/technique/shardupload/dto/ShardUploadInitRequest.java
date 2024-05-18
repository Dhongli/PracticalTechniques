package com.dai.technique.shardupload.dto;

import lombok.Data;

/**
 * <b>description</b>： initrequest <br>
 * <b>time</b>：2024/3/26 22:08 <br>
 * <b>author</b>：ready likun_557@163.com
 */
@Data
public class ShardUploadInitRequest {
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

}
