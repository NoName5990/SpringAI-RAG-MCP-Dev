package com.jixiaoliu.mcp.tool;

import com.jixiaoliu.bean.EmailRequest;
import com.jixiaoliu.common.FormConvert;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * @ClassName EmailTool
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/26 下午6:25
 */
@Component
@Slf4j
public class EmailTool implements McpTool{

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    private final static String MY_EMAIL_ADDRESS = "liujxiaom@163.com";

    public EmailTool(JavaMailSender javaMailSender, MailProperties mailProperties,
        JavaMailSenderImpl mailSender) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
    }

    @Tool(description = "发送邮件，支持指定收件人、主题和内容")
    public String sendEmail(@ToolParam(description = "邮件请求对象") EmailRequest emailRequest) {
        log.info("========== 调用MCP工具：sendEmail() ==========");
        log.info("========== 参数：[emailRequest:{}] ==========", emailRequest.toString());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mime = new MimeMessageHelper(mimeMessage);
        try {
            mime.setFrom(mailProperties.getUsername());
            mime.setTo(emailRequest.getTo());
            mime.setSubject(emailRequest.getSubject());
            mime.setText(FormConvert.convertToHtml(emailRequest.getContent()),true);
            javaMailSender.send(mimeMessage);
            return "邮件发送成功";
        } catch (MessagingException e) {
            log.error("邮件发送失败", e);
            throw new RuntimeException("邮件发送失败: " + e.getMessage(), e);
        }
    }

    @Tool(description = "查询我的邮箱")
    public String queryEmail() {
        log.info("========== 调用MCP工具：queryEmail() ==========");
        return MY_EMAIL_ADDRESS;
    }
}
