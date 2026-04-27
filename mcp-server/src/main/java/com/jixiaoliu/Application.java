package com.jixiaoliu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName Application
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/26 下午3:25
 */
@SpringBootApplication
@MapperScan({"com.jixiaoliu.mapper"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
