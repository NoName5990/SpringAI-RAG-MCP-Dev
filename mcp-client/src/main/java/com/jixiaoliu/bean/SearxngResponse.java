package com.jixiaoliu.bean;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SearxngResponse
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/24 下午5:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearxngResponse {
    private String query;
    private List<SearxngResult> results;
}
