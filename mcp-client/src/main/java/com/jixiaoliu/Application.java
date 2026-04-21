package com.jixiaoliu;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName Application
 * @Author liujxiao
 * @Version 1.0
 * @Description mcp-client 启动类
 * @date 2026/4/20 下午4:44
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // 加载.env文件为map
        Dotenv dotenv = Dotenv.configure().load();
        // 将.env中的变量配置到环境变量中
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(Application.class, args);
    }
}
