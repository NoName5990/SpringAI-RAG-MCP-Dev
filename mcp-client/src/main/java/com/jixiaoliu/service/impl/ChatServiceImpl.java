package com.jixiaoliu.service.impl;

import com.jixiaoliu.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.stereotype.Service;

/**
 * @ClassName ChatServiceImpl
 * @Author liujxiao
 * @Version 1.0
 * @Description ChatService实现类
 * @date 2026/4/20 下午5:56
 */
@Service
public class ChatServiceImpl implements ChatService {

    private ChatClient chatClient;

    private String sysPrompt =
                                """
                                    "你是一个非常聪明的人工智能助手，可以帮我解决很多问题。你的名字叫'双双'"。
                                """;

    public ChatServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
            .defaultSystem(sysPrompt)
            .build()
            ;
    }

    @Override
    public String chat(String msg) {
        ChatClientRequestSpec requestSpec = chatClient.prompt(msg);
        CallResponseSpec call = requestSpec.call();
        return call.content();
    }
}
