package com.jixiaoliu.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloWorldController
 * @Author liujxiao
 * @Version 1.0
 * @Description HelloWorldController
 * @date 2026/4/20 下午4:47
 */

@RestController
@RequestMapping("hello")
public class HelloWorldController {

    @GetMapping("world")
    public String getHello(){
        return "Hello World!";
    }
}
