package com.dai.technique.shardupload.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dai.technique.shardupload.dto.ShardUploadCompleteRequest;
import com.dai.technique.shardupload.dto.ShardUploadDetailResponse;
import com.dai.technique.shardupload.dto.ShardUploadInitRequest;
import com.dai.technique.shardupload.dto.ShardUploadPartRequest;
import com.dai.technique.shardupload.mapper.ShardUploadMapper;
import com.dai.technique.shardupload.mapper.ShardUploadPartMapper;
import com.dai.technique.shardupload.po.ShardUploadPO;
import com.dai.technique.shardupload.po.ShardUploadPartPO;
import com.dai.technique.shardupload.utils.IdUtils;
import com.dai.technique.shardupload.utils.ShardUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author daihongli
 * @version 1.0
 * @ClassName ShardUploadServiceImpl
 * @Description: TODO
 * @Date 2024-05-17 18:13
 */
@Service
public class ShardUploadServiceImpl extends ServiceImpl<ShardUploadMapper, ShardUploadPO> implements ShardUploadService {
    private final String SHARD_FILE_PATH = "D:/luren/shardupload/";

    @Autowired
    private ShardUploadPartMapper shardUploadPartMapper;

    @Override
    public String init(ShardUploadInitRequest request) {
        ShardUploadPO shardUpload = new ShardUploadPO();
        shardUpload.setId(IdUtils.generateId());
        shardUpload.setFileName(request.getFileName());
        shardUpload.setMd5(request.getMd5());
        shardUpload.setPartNum(request.getPartNum());
        this.save(shardUpload);
        return shardUpload.getId();
    }

    @Override
    public void uploadPart(ShardUploadPartRequest request) throws IOException {
        if (this.getUploadPartPO(request.getShardUploadId(), request.getPartOrder()) != null) {
            return;
        }
        String partFileFullPath = this.getPartFileFullPath(request.getShardUploadId(), request.getPartOrder());
        File file = new File(partFileFullPath);
        // 新文件不存在要创建一下
        ShardUploadUtils.createFileNotExists(file);
        // transferTo 将文件考到新文件， 新文件必须存在，否则报错
        request.getFile().transferTo(file);
        // 3、将分片文件信息写入db中
        this.saveShardUploadPart(request, partFileFullPath);
    }

    private String getPartFileFullPath(String shardUploadId, Integer partOrder) {
        return String.format(SHARD_FILE_PATH + "%s/%s", shardUploadId, partOrder);
    }

    @Override
    public void complete(ShardUploadCompleteRequest request) throws IOException {

    }

    @Override
    public ShardUploadDetailResponse detail(String shardUploadId) {
        return null;
    }


    private void saveShardUploadPart(ShardUploadPartRequest request, String partFileFullPath) {
        ShardUploadPartPO partPO = new ShardUploadPartPO();
        partPO.setId(IdUtils.generateId());
        partPO.setShardUploadId(request.getShardUploadId());
        partPO.setPartOrder(request.getPartOrder());
        partPO.setFileFullPath(partFileFullPath);
        shardUploadPartMapper.insert(partPO);
    }

    private ShardUploadPartPO getUploadPartPO(String shardUploadId, Integer partOrder) {
        LambdaQueryWrapper<ShardUploadPartPO> wq = Wrappers.lambdaQuery(ShardUploadPartPO.class)
                .eq(ShardUploadPartPO::getShardUploadId, shardUploadId)
                .eq(ShardUploadPartPO::getPartOrder, partOrder);
        return this.shardUploadPartMapper.selectOne(wq);
    }
}
