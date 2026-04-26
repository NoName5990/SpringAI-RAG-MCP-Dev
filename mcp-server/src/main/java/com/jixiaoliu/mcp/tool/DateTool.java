package com.jixiaoliu.mcp.tool;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
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
public class DateTool implements McpTool {


    /**
     * @param city
     * @param zoneId
     * @return java.lang.String
     * @Description: 根据城市所在的时区id获得当前时间
     * @Date 2026/4/26 下午5:32
     * @Author liujxiao
     */
    @Tool(description = "根据城市所在的时区id获得当前时间")
    public String getZoneCurrentTime(@ToolParam(description = "城市") String city,
        @ToolParam(description = "时区Id") String zoneId) {
        log.info("========== 调用MCP工具：getZoneCurrentTime() ==========");
        log.info("========== 参数：[city:{},zoneId:{}] ==========", city, zoneId);
        ZoneId zone = ZoneId.of(zoneId);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);
        return String.format("%s当前时间：%s", city,
            zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    /**
     * @return java.lang.String
     * @Description: 获得当前所在地时间
     * @Date 2026/4/26 下午5:32
     * @Author liujxiao
     */
    @Tool(description = "在不指定城市的时候，获得当前时间")
    public String getCurrentTime() {
        log.info("========== 调用MCP工具：getCurrentTime() ==========");
        return String.format("当前时间：%s",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
