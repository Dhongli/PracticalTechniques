package com.dai.technique.shardupload.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author daihongli
 * @version 1.0
 * @ClassName ShardUploadUtils
 * @Description: TODO
 * @Date 2024-05-17 20:34
 */
public class ShardUploadUtils {
    public static int shardNum(long fileSize, long partSize) {
        if (fileSize % partSize == 0) {
            // 可以整除
            return (int) (fileSize / partSize);
        }
        return (int) (fileSize / partSize + 1);
    }

    public static File createFileNotExists(File file) throws IOException {
        // 确保文件的父目录存在。如果父目录不存在，它会创建所有必要的父目录。
        if (!file.exists()) {
            FileUtils.forceMkdirParent(file);
            file.createNewFile();
        }
        return file;
    }
}
