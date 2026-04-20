package com.jixiaoliu.controller;

import com.jixiaoliu.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("conver")
    public String chat(String msg) {
        return chatService.chat(msg);
    }


}
