package com.dai.technique.shardupload.controller;

import cn.hutool.crypto.SecureUtil;
import com.dai.technique.shardupload.comm.Result;
import com.dai.technique.shardupload.dto.ShardUploadInitRequest;
import com.dai.technique.shardupload.utils.ShardUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


@Slf4j
class ShardUploadControllerTest {

    RestTemplate restTemplate = new RestTemplate();

    File file = new File("F:\\普中\\STM32自学笔记.pdf");

    // 20M
    long partSize = 20 * 1024 * 1024;

    @Test
    void shardUpload() {
        String fileMd5 = SecureUtil.md5(file);
        int partNum = ShardUploadUtils.shardNum(file.length(), partSize);
        log.info("分片上传，初始化，文件:{}", file.getAbsoluteFile());
        log.info("分片数量:{},分片文件大小：{},文件md5:{}", partNum, partNum, fileMd5);

        // 1、分片上传初始化
        String shardUploadId = shardUploadInit(file.getName(), fileMd5, partNum);
        log.info(shardUploadId);

        // 2、循环上传分片
        for (int partOrder = 1; partOrder <= partNum; partOrder++) {
            shardUploadPart(shardUploadId, partOrder);
        }

    }

    private void shardUploadPart(String shardUploadId, int partOrder) {
        byte[] bytes = readPart(partOrder);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("shardUploadId", shardUploadId);
        body.add("partOrder", partOrder);
        body.add("file", new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {
                return "part" + partOrder;
            }
        });
        RequestEntity<MultiValueMap<String, Object>> entity = RequestEntity
                .post(getRequestUrl("/shardUpload/uploadPart"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body);
        restTemplate.exchange(entity, new ParameterizedTypeReference<Result<String>>() {
        });
    }

    private byte[] readPart(int partOrder) {
        byte[] bytes = new byte[(int) partSize];
        RandomAccessFile randomAccessFile = null;

        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek((partOrder - 1) * partSize);
            int read = randomAccessFile.read(bytes);
            if (read == partSize) {
                return bytes;
            } else {
                byte[] tempBytes = new byte[read];
                System.arraycopy(bytes, 0, tempBytes, 0, read);
                return tempBytes;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(randomAccessFile);
        }

    }

    private String shardUploadInit(String fileName, String fileMd5, int partNum) {
        ShardUploadInitRequest request = new ShardUploadInitRequest();
        request.setFileName(fileName);
        request.setPartNum(partNum);
        request.setMd5(fileMd5);
        RequestEntity<ShardUploadInitRequest> entity = RequestEntity
                .post(getRequestUrl("/shardUpload/init"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(request);
        ResponseEntity<Result<String>> response = restTemplate.exchange(entity, new ParameterizedTypeReference<Result<String>>() {
        });
        return response.getBody().getData();
    }

    public String getRequestUrl(String path) {
        return String.format("http://localhost:8080/%s", path);
    }
}