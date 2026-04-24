package com.jixiaoliu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SearxngResult
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/24 下午3:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearxngResult {
    private String title;
    private String url;
    private String content;
    private float score;
}
