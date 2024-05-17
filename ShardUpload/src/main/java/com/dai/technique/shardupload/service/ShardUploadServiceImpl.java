package com.dai.technique.shardupload.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dai.technique.shardupload.dto.ShardUploadCompleteRequest;
import com.dai.technique.shardupload.dto.ShardUploadDetailResponse;
import com.dai.technique.shardupload.dto.ShardUploadInitRequest;
import com.dai.technique.shardupload.dto.ShardUploadPartRequest;
import com.dai.technique.shardupload.mapper.ShardUploadMapper;
import com.dai.technique.shardupload.po.ShardUploadPO;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author daihongli
 * @version 1.0
 * @ClassName ShardUploadServiceImpl
 * @Description: TODO
 * @Date 2024-05-17 18:13
 */
@Service
public class ShardUploadServiceImpl extends ServiceImpl<ShardUploadMapper, ShardUploadPO> implements ShardUploadService{
    @Override
    public String init(ShardUploadInitRequest request) {
        return null;
    }

    @Override
    public void uploadPart(ShardUploadPartRequest request) throws IOException {

    }

    @Override
    public void complete(ShardUploadCompleteRequest request) throws IOException {

    }

    @Override
    public ShardUploadDetailResponse detail(String shardUploadId) {
        return null;
    }
}
