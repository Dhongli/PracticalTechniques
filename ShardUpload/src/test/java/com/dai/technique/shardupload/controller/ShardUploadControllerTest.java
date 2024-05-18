package com.dai.technique.shardupload.controller;

import cn.hutool.crypto.SecureUtil;
import com.dai.technique.shardupload.comm.Result;
import com.dai.technique.shardupload.dto.ShardUploadCompleteRequest;
import com.dai.technique.shardupload.dto.ShardUploadDetailResponse;
import com.dai.technique.shardupload.dto.ShardUploadInitRequest;
import com.dai.technique.shardupload.dto.ShardUploadPartRequest;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


@Slf4j
class ShardUploadControllerTest {

    RestTemplate restTemplate = new RestTemplate();

    File file = new File("F:\\普中\\STM32自学笔记.pdf");

    // 20M
    long partSize = 20 * 1024 * 1024;

    @Test
    void shardUpload() throws IOException {
        String fileMd5 = SecureUtil.md5(file);
        // 根据文件大小算分多少个，一个20M
        int partNum = ShardUploadUtils.shardNum(file.length(), partSize);

        // 1、分片上传初始化
        String shardUploadId = shardUploadInit(file.getName(), fileMd5, partNum);

        for (int i = 1; i <= partNum; i++) {
            // 2、循环上传分片
            shardUploadPart(shardUploadId, i);
        }

        // 3、合并分片文件
        shardUploadComplete(shardUploadId);

        // 4、获取分片任务的详细信息(分片是否上传完成、哪些分片文件是否已上传)
        ShardUploadDetailResponse detail = this.shardUploadDetail(shardUploadId);
        log.info("分片任务详细信息:{}", detail);
    }

    private ShardUploadDetailResponse shardUploadDetail(String shardUploadId) {
        RequestEntity<Void> entity = RequestEntity.get(getRequestUrl("/shardUpload/detail?shardUploadId=" + shardUploadId))
                .build();
        ResponseEntity<Result<ShardUploadDetailResponse>> exchange = restTemplate.exchange(entity, new ParameterizedTypeReference<Result<ShardUploadDetailResponse>>() {
        });
        return exchange.getBody().getData();
    }

    private void shardUploadComplete(String shardUploadId) {
        ShardUploadCompleteRequest request = new ShardUploadCompleteRequest();
        request.setShardUploadId(shardUploadId);
        RequestEntity<ShardUploadCompleteRequest> entity = RequestEntity
                .post(getRequestUrl("/shardUpload/complete"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(request);
        ResponseEntity<Result<Boolean>> exchange = restTemplate.exchange(entity, new ParameterizedTypeReference<Result<Boolean>>() {
        });
        log.info("文件上传完成：{}", exchange.getBody());
    }

    private String shardUploadInit(String fileName, String fileMd5, int partNum) {
        ShardUploadInitRequest request = new ShardUploadInitRequest();
        request.setMd5(fileMd5);
        request.setFileName(fileName);
        request.setPartNum(partNum);
        RequestEntity<ShardUploadInitRequest> entity = RequestEntity
                .post(getRequestUrl("/shardUpload/init"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(request);

        ResponseEntity<Result<String>> response = restTemplate.exchange(entity, new ParameterizedTypeReference<Result<String>>() {
        });
        return response.getBody().getData();
    }

    private void shardUploadPart(String shardUploadId, int partOrder) throws IOException {

        byte[] bytes = readPart(partOrder);

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("shardUploadId", shardUploadId);
        multiValueMap.add("partOrder", partOrder);
        multiValueMap.add("file", new ByteArrayResource(bytes) {
            // 不重写getName, filename就为null, 报错
            @Override
            public String getFilename() {
                return "part";
            }
        });
        RequestEntity<MultiValueMap<String, Object>> entity = RequestEntity
                .post(getRequestUrl("/shardUpload/uploadPart"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multiValueMap);
        ResponseEntity<Result<Boolean>> exchange = restTemplate.exchange(entity, new ParameterizedTypeReference<Result<Boolean>>() {
        });
    }

    private byte[] readPart(int partOrder) throws IOException {
        byte[] bytes = new byte[(int) partSize];
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            randomAccessFile.seek((partOrder - 1) * partSize);
            int read = randomAccessFile.read(bytes);
            if (read == partSize) {
                return bytes;
            } else {
                byte[] byteTemp = new byte[read];
                System.arraycopy(bytes, 0, byteTemp, 0, read);
                return byteTemp;
            }
        }
    }

    public String getRequestUrl(String path) {
        return String.format("http://localhost:8080/%s", path);
    }
}