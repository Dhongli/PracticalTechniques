package com.dai.technique.shardupload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.dai.technique.shardupload.mapper")
public class SharduploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(SharduploadApplication.class, args);
    }
}