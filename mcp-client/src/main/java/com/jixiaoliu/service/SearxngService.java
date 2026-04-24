package com.jixiaoliu.service;

import com.jixiaoliu.bean.SearxngResult;
import java.util.List;

/**
 * @ClassName SearxngService
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/24 下午3:32
 */
public interface SearxngService {

    /**
     * @Description: searxng网络查询
     * @Date 2026/4/24 下午3:32
     * @Author liujxiao
     * @param query
     * @return java.util.List<com.jixiaoliu.bean.SearxngResult>
     */
    public List<SearxngResult> websearch(String query);
}
