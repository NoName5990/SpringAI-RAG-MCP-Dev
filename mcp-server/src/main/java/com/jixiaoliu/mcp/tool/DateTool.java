package com.jixiaoliu.mcp.tool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

/**
 * @ClassName DateTool
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/26 下午3:47
 */
@Component
@Slf4j
public class DateTool {

    @Tool(description = "获得当前时间")
    public String getCurrentTime() {
        log.info("========== 调用MCP工具：getCurrentTime() ==========");
        return String.format("当前时间：%s",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
