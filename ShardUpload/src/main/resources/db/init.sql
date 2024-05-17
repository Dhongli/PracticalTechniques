CREATE DATABASE IF NOT EXISTS luren
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;


-- 2张表，t_shard_upload：分片任务表，t_shard_upload_part：分片文件表，两个表是1：n的关系
create table if not exists t_shard_upload(
    id varchar(32) primary key,
    file_name varchar(256) not null comment '文件名称',
    part_num int not null comment '分片数量',
    md5 varchar(128) comment '文件md5值',
    file_full_path varchar(512) comment '文件完整路径'
) comment = '分片上传任务表';


create table if not exists  t_shard_upload_part(
    id varchar(32) primary key,
    shard_upload_id varchar(32) not null comment '分片任务id（t_shard_upload.id）',
    part_order int not null comment '第几个分片，从1开始',
    file_full_path varchar(512) comment '文件完整路径',
    UNIQUE KEY `uq_part_order` (`shard_upload_id`,`part_order`)
) comment = '分片文件表，每个分片文件对应一条记录';
