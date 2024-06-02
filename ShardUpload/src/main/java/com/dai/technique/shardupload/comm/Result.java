package com.dai.technique.shardupload.comm;

import lombok.Data;

/**
 * @Description: rest风格返回包装类
 * @date 2024-06-02 9:13
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
