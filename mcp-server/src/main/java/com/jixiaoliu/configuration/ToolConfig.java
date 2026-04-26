package com.jixiaoliu.configuration;

import com.jixiaoliu.mcp.tool.DateTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ToolConfig
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/26 下午3:50
 */
@Configuration
public class ToolConfig {

    @Bean
    public ToolCallbackProvider toolCallbackProvider(DateTool dateTool) {
        return MethodToolCallbackProvider.builder()
            .toolObjects(dateTool)
            .build();
    }
}
