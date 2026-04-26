package com.jixiaoliu.service.impl;

import cn.hutool.json.JSONUtil;
import com.jixiaoliu.bean.ChatEntity;
import com.jixiaoliu.bean.ChatResponseEntity;
import com.jixiaoliu.bean.SearxngResult;
import com.jixiaoliu.enums.SSEMsgType;
import com.jixiaoliu.service.ChatService;
import com.jixiaoliu.service.DocumentService;
import com.jixiaoliu.service.SearxngService;
import com.jixiaoliu.utils.SSEServer;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallbackProvider;
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
    @Resource
    private SearxngService searxngService;

    private static final String SYS_PROMPT =
        "你是一个非常聪明的人工智能助手，可以帮我解决很多问题。你的名字叫'双双'。";

    public ChatServiceImpl(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = chatClientBuilder
            .defaultToolCallbacks(toolCallbackProvider)
            // .defaultSystem(SYS_PROMPT)
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
            // log.info("content: {}", content);
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

        String context = documents.stream().map(Document::getText)
            .collect(Collectors.joining("\n"));

        Prompt prompt = new Prompt(RAG_SYS_PROMPT
            .replace("{context}", context)
            .replace("{question}", userPrompt));

        sendPrompt(userId, botMsgId, prompt);
    }

    private final static String SEARXNG_SYS_PROMPT = """
        你是一个网络智能问答小助手，请严格基于通过网络搜索得到的【上下文内容】来回答【用户问题】。
        
        要求：
        1. 如果上下文中包含足以回答问题的信息，请组织语言清晰、准确地回答。
        2. 如果上下文中没有相关信息或信息不足以回答问题，请直接回答：“未搜索到相关内容”，不要编造事实。
        3. 保持回答简洁、专业，避免冗余的开场白。
        
        【上下文内容】：
        ```
        {context}
        ```
        
        【用户问题】：
        '''
        {question}
        '''
        【回答】：
        如果没有就回答不知道，不要回答不相关的内容。
        """;

    @Override
    public void doSearxngSearch(ChatEntity chatEntity) {

        String userId = chatEntity.getCurrentUserName();
        String userPrompt = chatEntity.getMessage();
        String botMsgId = chatEntity.getBotMsgId();

        List<SearxngResult> results = searxngService.websearch(userPrompt);

        Prompt prompt = buildSearxngPrompt(results, userPrompt);

        // 打印输出
        // System.out.println(prompt.toString());

        sendPrompt(userId, botMsgId, prompt);
    }

    private void sendPrompt(String userId, String botMsgId, Prompt prompt) {
        Flux<String> content = chatClient.prompt(prompt).stream().content();

        List<String> list = content.toStream().map(resp -> {
            String contentText = resp.toString();
            SSEServer.sendMessage(userId, contentText, SSEMsgType.ADD);
            // 打印输出
            // log.info("content: {}", contentText);
            return contentText;
        }).toList();

        String fullContent = list.stream().collect(Collectors.joining());
        ChatResponseEntity chatResponseEntity = new ChatResponseEntity(fullContent, botMsgId);
        SSEServer.sendMessage(userId, JSONUtil.toJsonStr(chatResponseEntity), SSEMsgType.FINISH);
    }

    private Prompt buildSearxngPrompt(List<SearxngResult> results, String question) {
        StringBuilder context = new StringBuilder();
        results.forEach(result -> {
                        context.append(String.format("<context>\n 【摘要】 %s \n 【来源】 %s \n</context> \n",
                        result.getContent(), result.getUrl()));
        });
        return new Prompt(SEARXNG_SYS_PROMPT
            .replace("{context}", context)
            .replace("{question}", question));
    }
}
