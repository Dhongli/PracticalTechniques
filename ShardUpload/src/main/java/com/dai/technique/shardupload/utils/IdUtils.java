package com.dai.technique.shardupload.utils;

import cn.hutool.Hutool;

import java.util.UUID;

/**
 * @author daihongli
 * @version 1.0
 * @ClassName IdUtils
 * @Description: TODO
 * @Date 2024-05-17 20:06
 */
public class IdUtils {
    public static String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
