package com.jixiaoliu.service.impl;

import cn.hutool.json.JSONUtil;
import com.jixiaoliu.bean.ChatEntity;
import com.jixiaoliu.bean.ChatResponseEntity;
import com.jixiaoliu.enums.SSEMsgType;
import com.jixiaoliu.service.ChatService;
import com.jixiaoliu.service.DocumentService;
import com.jixiaoliu.utils.SSEServer;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
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
    @Resource
    private DocumentService documentService;

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

        List<String> list = response.toStream().map(resp -> {
            String content = resp.toString();
            SSEServer.sendMessage(userId, content, SSEMsgType.ADD);
            log.info("content: {}", content);
            return content;
        }).toList();

        String fullContent = list.stream().collect(Collectors.joining());
        ChatResponseEntity chatResponseEntity = new ChatResponseEntity(fullContent, botMsgId);
        SSEServer.sendMessage(userId, JSONUtil.toJsonStr(chatResponseEntity), SSEMsgType.FINISH);

    }

    private final static String RAG_SYS_PROMPT = """
            你是一个智能问答助手，请严格基于以下提供的【上下文内容】来回答【用户问题】。
            
            要求：
            1. 如果上下文中包含足以回答问题的信息，请组织语言清晰、准确地回答。
            2. 如果上下文中没有相关信息或信息不足以回答问题，请直接回答：“根据当前知识库，我暂时无法找到相关答案。”，不要编造事实。
            3. 保持回答简洁、专业，避免冗余的开场白。
            
            【上下文内容】：
            ```
            {context}
            ```
            
            【用户问题】：
            '''
            {question}
            '''
            """;
    @Override
    public void doRagSearch(ChatEntity chatEntity) {

        String userId = chatEntity.getCurrentUserName();
        String userPrompt = chatEntity.getMessage();
        String botMsgId = chatEntity.getBotMsgId();

        List<Document> documents = documentService.doSearch(chatEntity.getMessage());
        if (documents.isEmpty()) {
            SSEServer.sendMessage(userId, "没有找到答案", SSEMsgType.FINISH);
        }

        String context = documents.stream().map(Document::getText).collect(Collectors.joining("\n"));

        Prompt prompt = new Prompt(RAG_SYS_PROMPT
            .replace("{context}", context)
            .replace("{question}", userPrompt));

        Flux<String> content = chatClient.prompt(prompt).stream().content();

        List<String> list = content.toStream().map(resp -> {
            String contentText = resp.toString();
            SSEServer.sendMessage(userId, contentText, SSEMsgType.ADD);
            // log.info("content: {}", content);
            return contentText;
        }).toList();

        String fullContent = list.stream().collect(Collectors.joining());
        ChatResponseEntity chatResponseEntity = new ChatResponseEntity(fullContent, botMsgId);
        SSEServer.sendMessage(userId, JSONUtil.toJsonStr(chatResponseEntity), SSEMsgType.FINISH);
    }
}
