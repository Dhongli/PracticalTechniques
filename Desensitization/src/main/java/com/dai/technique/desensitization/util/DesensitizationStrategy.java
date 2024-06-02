package com.dai.technique.desensitization.util;

import cn.hutool.core.util.DesensitizedUtil;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum DesensitizationStrategy {
    PHONE(s -> DesensitizedUtil.desensitized(s, DesensitizedUtil.DesensitizedType.MOBILE_PHONE)),

    // 邮箱，保留前一位即@和后面的部分，其他的替换为*
    EMAIL(s -> DesensitizedUtil.desensitized(s, DesensitizedUtil.DesensitizedType.EMAIL)),

    // 身份证号，保留前一位和后2位，其他的替换为*
    ID_CARD(s -> DesensitizedUtil.desensitized(s, DesensitizedUtil.DesensitizedType.ID_CARD)),

    // 地址，保留前四位，其他的替换为*
    ADDRESS(s -> DesensitizedUtil.desensitized(s, DesensitizedUtil.DesensitizedType.ADDRESS)),

    // 银行卡，保留前后四位，其他的替换为*
    BANK_CARD(s -> DesensitizedUtil.desensitized(s, DesensitizedUtil.DesensitizedType.BANK_CARD)),

    // 姓名，保留前一位，其他的替换为*
    NAME(s -> DesensitizedUtil.desensitized(s, DesensitizedUtil.DesensitizedType.CHINESE_NAME)),

    // 密码，统一替换为 ******
    PASSWORD(s -> "******");


    private final Function<String, String> desensitization;

    DesensitizationStrategy(Function<String, String> desensitization) {
        this.desensitization = desensitization;
    }
}
