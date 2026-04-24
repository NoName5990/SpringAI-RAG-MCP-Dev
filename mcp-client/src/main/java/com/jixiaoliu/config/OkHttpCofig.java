package com.jixiaoliu.config;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName OkHttpCofig
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/24 下午4:17
 */
@Configuration
public class OkHttpCofig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    }

}

