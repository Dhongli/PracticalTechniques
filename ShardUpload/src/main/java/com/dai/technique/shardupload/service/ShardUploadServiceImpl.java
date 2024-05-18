package com.dai.technique.shardupload.service;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dai.technique.shardupload.comm.ServiceExceptionUtils;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daihongli
 * @version 1.0
 * @ClassName ShardUploadServiceImpl
 * @Description: TODO
 * @Date 2024-05-17 18:13
 */
@Service
public class ShardUploadServiceImpl extends ServiceImpl<ShardUploadMapper, ShardUploadPO> implements ShardUploadService {
    private final String SHARD_FILE_PATH = "D:/learn/shardupload/";

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
        // 如果改文件片已存在则直接返回
        if (getUploadPartPO(request.getShardUploadId(), request.getPartOrder()) != null) {
            return;
        }
        String fileFullPath = getPartFileFullPath(request.getShardUploadId(), request.getPartOrder());
        File file = new File(fileFullPath);
        // 判断要输出的文件是否已存在，不存在创建
        ShardUploadUtils.createFileNotExists(file);
        // transferTo 将文件考到新文件， 新文件必须存在，否则报错
        request.getFile().transferTo(file);

        // 将分片文件信息写入数据库
        this.saveShardUploadPart(request, fileFullPath);
    }

    @Override
    public void complete(ShardUploadCompleteRequest request) throws IOException {
        // 1、获取分片任务 && 分片文件列表
        ShardUploadPO shardUpload = this.getById(request.getShardUploadId());
        if (shardUpload == null) {
            throw ServiceExceptionUtils.exception("分片任务不存在");
        }
        List<ShardUploadPartPO> shardUploadPartList = this.getShardUploadPartList(request.getShardUploadId());
        if (shardUploadPartList.size() != shardUpload.getPartNum()) {
            throw ServiceExceptionUtils.exception("分片还未上传完毕");
        }

        // 2、合并分片文件
        File file = this.mergeFile(shardUpload, shardUploadPartList);

        // 3、将最终的文件信息写到db中
        shardUpload.setFileFullPath(file.getAbsolutePath());
        this.updateById(shardUpload);
    }

    @Override
    public ShardUploadDetailResponse detail(String shardUploadId) {
        ShardUploadPO shardUploadPO = getById(shardUploadId);
        if (shardUploadPO == null) {
            return null;
        }
        ShardUploadDetailResponse response = new ShardUploadDetailResponse();
        List<ShardUploadPartPO> shardUploadPartList = getShardUploadPartList(shardUploadId);
        response.setShardUploadId(shardUploadId);
        response.setPartNum(shardUploadPO.getPartNum());
        response.setPartOrderList(shardUploadPartList.stream().map(ShardUploadPartPO::getPartOrder).collect(Collectors.toList()));
        response.setSuccess(shardUploadPO.getPartNum() == shardUploadPartList.size());
        return response;
    }

    private File mergeFile(ShardUploadPO shardUpload, List<ShardUploadPartPO> shardUploadPartList) throws IOException {
        File file = new File(getFileFullName(shardUpload));

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = FileUtils.openOutputStream(file, true);
            for (ShardUploadPartPO part : shardUploadPartList) {
                File partFile = new File(part.getFileFullPath());
                try (FileInputStream fileInputStream = FileUtils.openInputStream(partFile)) {
                    // IOUtils.copy 和 IOUtils.copyLarge 都是 Apache Commons IO 库中的方法，用于复制数据流。
                    IOUtils.copyLarge(fileInputStream, fileOutputStream);
                }
                partFile.delete();
            }
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }

        // 校验合并后的文件和目标文件的md5字是否一致
        if (!StringUtils.equals(shardUpload.getMd5(), SecureUtil.md5(file))) {
            throw ServiceExceptionUtils.exception("文件md5不匹配");
        }
        return file;
    }

    private String getFileFullName(ShardUploadPO shardUpload) {
        return String.format(SHARD_FILE_PATH + "%s/%s", shardUpload.getId(), shardUpload.getFileName());
    }

    private List<ShardUploadPartPO> getShardUploadPartList(String shardUploadId) {
        return shardUploadPartMapper.selectList(Wrappers
                .lambdaQuery(ShardUploadPartPO.class)
                .eq(ShardUploadPartPO::getShardUploadId, shardUploadId)
                .orderByAsc(ShardUploadPartPO::getPartOrder));
    }


    private void saveShardUploadPart(ShardUploadPartRequest request, String partFileFullPath) {
        ShardUploadPartPO shardUploadPart = new ShardUploadPartPO();
        shardUploadPart.setId(IdUtils.generateId());
        shardUploadPart.setPartOrder(request.getPartOrder());
        shardUploadPart.setShardUploadId(request.getShardUploadId());
        shardUploadPart.setFileFullPath(partFileFullPath);
        shardUploadPartMapper.insert(shardUploadPart);
    }

    private ShardUploadPartPO getUploadPartPO(String shardUploadId, Integer partOrder) {
        LambdaQueryWrapper<ShardUploadPartPO> wq = Wrappers.lambdaQuery(ShardUploadPartPO.class)
                .eq(ShardUploadPartPO::getShardUploadId, shardUploadId)
                .eq(ShardUploadPartPO::getPartOrder, partOrder);
        return shardUploadPartMapper.selectOne(wq);
    }

    private String getPartFileFullPath(String shardUploadId, Integer partOrder) {
        return String.format(SHARD_FILE_PATH + "%s/%s", shardUploadId, partOrder);
    }
}
