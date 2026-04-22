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

    // 硬编码即可
    private static final String ENV_FILE = ".env.qwen";

    public static void main(String[] args) {
        // 加载.env文件中的变量到系统属性
        Dotenv dotenv = Dotenv.configure().filename(ENV_FILE).load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(Application.class, args);
    }
}
