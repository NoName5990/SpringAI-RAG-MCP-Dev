package com.jixiaoliu.service.impl;

import cn.hutool.json.JSONUtil;
import com.jixiaoliu.bean.SearxngResponse;
import com.jixiaoliu.bean.SearxngResult;
import com.jixiaoliu.service.SearxngService;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName SearxngServiceImpl
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/24 下午3:33
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SearxngServiceImpl implements SearxngService {

    private final OkHttpClient okHttpClient;

    @Value("${websearch.searxng.url}")
    private String SEARXNG_URL;

    @Value("${websearch.searxng.counts}")
    private Integer COUNTS;



    @Override
    public List<SearxngResult> websearch(String query) {

        HttpUrl httpUrl = HttpUrl.get(SEARXNG_URL)
            .newBuilder()
            .addQueryParameter("q", query)
            .addQueryParameter("format", "json")
            .build();

        log.info("搜索url地址为：{}", httpUrl.url());

        // 构建request
        Request request = new Request.Builder()
            .url(httpUrl)
            .build();

        try (Response resp = okHttpClient.newCall(request).execute()) {

            // 判断请求是否成功
            if (!resp.isSuccessful()) throw new RuntimeException("请求失败：HTTP " + resp.code());

            // 获取数据
            if (resp.body() != null) {
                // string()与toString()差别很大，切记
                String body = resp.body().string();

                SearxngResponse searXNGResponse = JSONUtil.toBean(body, SearxngResponse.class);

                return dealResults(searXNGResponse.getResults());
            }

            log.error("搜索失败，{}", resp.message());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Collections.emptyList();
    }

    /**
     * @Description: 处理结果集
     * @Date 2026/4/24 下午5:36
     * @Author liujxiao
     * @param results
     * @return java.util.List<com.jixiaoliu.bean.SearxngResult>
     */
    private List<SearxngResult> dealResults(List<SearxngResult> results) {
        return results.subList(0,Math.min(COUNTS,results.size()))
                            .parallelStream()
                            .sorted(Comparator.comparingDouble(SearxngResult::getScore).reversed())
                            .limit(COUNTS)
                            .toList();

    }
}
