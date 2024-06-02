package com.dai.technique.desensitization.dto;


import com.dai.technique.desensitization.util.Desensitization;
import com.dai.technique.desensitization.util.DesensitizationStrategy;
import lombok.Data;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/4/17 15:29 <br>
 * <b>author</b>：ready likun_557@163.com
 */
@Data
public class User {
    // id
    private String id;
    // 姓名
    @Desensitization(DesensitizationStrategy.NAME)
    private String name;
    // 手机号
    @Desensitization(DesensitizationStrategy.PHONE)
    private String phone;
    // 邮箱
    @Desensitization(DesensitizationStrategy.EMAIL)
    private String email;
    // 银行卡
    @Desensitization(DesensitizationStrategy.ID_CARD)
    private String idCard;
    // 密码
    @Desensitization(DesensitizationStrategy.PASSWORD)
    private String password;

    // 地址
    @Desensitization(DesensitizationStrategy.ADDRESS)
    private String address;

    //银行卡
    @Desensitization(DesensitizationStrategy.BANK_CARD)
    private String bankCard;
}
