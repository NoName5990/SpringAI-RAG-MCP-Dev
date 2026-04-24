package com.jixiaoliu.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.ai.transformer.splitter.TextSplitter;

/**
 * @ClassName CustomTextSplitter
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/23 下午1:57
 */
public class CustomTextSplitter extends TextSplitter {

    @Override
    protected List<String> splitText(String text) {
        return List.of(split(text)).stream().map(String::trim).collect(Collectors.toList());
    }

    /**
     * @Description: 正则表达式 分割
     * @Date 2026/4/23 下午2:00
     * @Author liujxiao
     * @param text
     * @return java.lang.String[]
     */
    private String[] split(String text) {
        return text.split("\\s*\\R\\s*\\R\\s*");
    }

}
