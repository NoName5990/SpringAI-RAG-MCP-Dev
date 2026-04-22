package com.jixiaoliu.service.impl;

import com.jixiaoliu.bean.ChatEntity;
import com.jixiaoliu.enums.SSEMsgType;
import com.jixiaoliu.service.ChatService;
import com.jixiaoliu.utils.SSEServer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
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
@Slf4j
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

    @Override
    public void doChat(ChatEntity chatEntity) {
        String userId = chatEntity.getCurrentUserName();
        String userPrompt = chatEntity.getMessage();
        String botMsgId = chatEntity.getBotMsgId();
        Flux<String> response = chatClient.prompt().user(userPrompt).stream().content();
        response.toStream().forEach(resp-> {
            SSEServer.sendMessage(userId, resp, SSEMsgType.ADD);
            log.info("content: {}", resp);
        });
        /*List<String> list = response.toStream().map(resp -> {
            String content = resp.toString();
            SSEServer.sendMessage(userId, content, SSEMsgType.ADD);
            log.info("content: {}", content);
            return content;
        }).toList();*/
    }
}
