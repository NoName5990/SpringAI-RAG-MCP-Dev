package com.jixiaoliu.controller;

import cn.hutool.http.server.HttpServerResponse;
import com.jixiaoliu.service.ChatService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @ClassName ChatController
 * @Author liujxiao
 * @Version 1.0
 * @Description 对话控制类
 * @date 2026/4/20 下午6:02
 */
@RestController
@RequestMapping("chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    /**
     * @Description: 简单对话
     * @Date 2026/4/21 上午11:47
     * @Author liujxiao
     * @param msg
     * @return java.lang.String
     */
    @GetMapping("conver")
    public String chat(String msg) {
        return chatService.chat(msg);
    }

    /**
     * @Description: 流式输出
     * @Date 2026/4/21 上午11:45
     * @Author liujxiao
     * @param msg
     * @param response
     * @return reactor.core.publisher.Flux<java.lang.String>
     */
    @GetMapping("flux")
    public Flux<String> flux(String msg, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return chatService.chatFlux(msg);
    }
}
