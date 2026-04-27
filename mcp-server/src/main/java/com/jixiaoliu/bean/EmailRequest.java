package com.jixiaoliu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName EmailRequest
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/26 下午6:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class EmailRequest {

    @ToolParam(description = "邮件主题")
    private String subject;

    @ToolParam(description = "邮件内容")
    private String content;

    @ToolParam(description = "收件人")
    private String to;

    @ToolParam(description = "邮件内容是否为html还是markdown格式。如果是Markdown格式，则为1；如果是html格式，则为2.")
    private Integer contentType;
}
