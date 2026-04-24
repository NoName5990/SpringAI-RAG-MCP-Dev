package com.jixiaoliu.service;

import com.jixiaoliu.bean.ChatEntity;
import reactor.core.publisher.Flux;

/**
 * @ClassName ChatService
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/20 下午5:55
 */
public interface ChatService {

    public String chat(String msg);

    public Flux<String> chatFlux(String msg);

    /**
     * @Description: 模型对话
     * @Date 2026/4/22 下午10:48
     * @Author liujxiao
     * @param chatEntity
     * @return void
     */
    void doChat(ChatEntity chatEntity);

    /**
     * @Description: rag查询
     * @Date 2026/4/23 下午3:29
     * @Author liujxiao
     * @param chatEntity
     * @return void
     */
    void doRagSearch(ChatEntity chatEntity);

    /**
     * @Description: searxng联网查询
     * @Date 2026/4/24 下午6:08
     * @Author liujxiao
     * @param chatEntity
     * @return void
     */
    void doSearxngSearch(ChatEntity chatEntity);
}
