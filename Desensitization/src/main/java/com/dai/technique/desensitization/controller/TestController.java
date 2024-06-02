package com.dai.technique.desensitization.controller;

import com.dai.technique.desensitization.common.Result;
import com.dai.technique.desensitization.common.ResultUtils;
import com.dai.technique.desensitization.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>description</b>： Java高并发、微服务、性能优化实战案例100讲，视频号：程序员路人，源码 & 文档 & 技术支持，请加个人微信号：itsoku <br>
 * <b>time</b>：2024/4/17 6:50 <br>
 * <b>author</b>：ready likun_557@163.com
 */
@RestController
public class TestController {
    @GetMapping("/getUser")
    public Result<User> getUser() {
        User user = new User();
        user.setId("001");
        user.setName("路人");
        user.setPhone("18612345678");
        user.setEmail("likun_557@163.com");
        user.setIdCard("420123432112121332");
        user.setPassword("123456");
        user.setAddress("上海市闵行区漕河泾开发区");
        user.setBankCard("6226090211114567");
        System.out.println(user);
        return ResultUtils.success(user);
    }
}
