package com.jixiaoliu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName CorsConfig
 * @Author liujxiao
 * @Version 1.0
 * @Description 跨域配置类
 * @date 2026/4/22 下午5:39
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.domain}")
    private String domain;

    // ctrl + i查看实现的方法
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
            .allowedOrigins(domain)
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowCredentials(true)
            .maxAge(60*60);
    }
}
