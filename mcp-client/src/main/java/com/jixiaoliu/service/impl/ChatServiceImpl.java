package com.jixiaoliu.service.impl;

import com.jixiaoliu.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @ClassName ChatServiceImpl
 * @Author liujxiao
 * @Version 1.0
 * @Description ChatService实现类
 * @date 2026/4/20 下午5:56
 */
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    private static final String SYS_PROMPT = "你是一个非常聪明的人工智能助手，可以帮我解决很多问题。你的名字叫'双双'。";

    public ChatServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
            .defaultSystem(SYS_PROMPT)
            .build();
    }

    @Override
    public String chat(String msg) {
        return chatClient.prompt().user(msg).call().content();
    }

    @Override
    public Flux<String> chatFlux(String msg) {
        // Flux<ChatResponse> response = chatClient.prompt(sysPrompt).stream().chatResponse();
        return chatClient.prompt(msg).stream().content();
    }
}
