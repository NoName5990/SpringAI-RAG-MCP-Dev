package com.jixiaoliu.controller;

import com.jixiaoliu.bean.ChatEntity;
import com.jixiaoliu.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * @Description: 模型对话
     * @Date 2026/4/22 下午10:48
     * @Author liujxiao
     * @param chatEntity
     * @return void
     */
    @PostMapping("doChat")
    public void doChat(@RequestBody ChatEntity chatEntity) {
        chatService.doChat(chatEntity);
    }

}
