package com.jixiaoliu.utils;

import com.jixiaoliu.enums.SSEMsgType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @ClassName SSEServer
 * @Author liujxiao
 * @Version 1.0
 * @Description SSEServer类
 * @date 2026/4/21 下午11:06
 */
@Slf4j
public class SSEServer {

    private static final Map<String, SseEmitter> sseClients = new HashMap<String, SseEmitter>();

    /**
     * @param userId
     * @return org.springframework.web.servlet.mvc.method.annotation.SseEmitter
     * @Description: 连接SSE服务
     * @Date 2026/4/21 下午11:20
     * @Author liujxiao
     */
    public static SseEmitter connect(String userId) {

        if (sseClients.containsKey(userId)) {
            return sseClients.get(userId);
        }

        // 设置超时时间，OL代表用不超时，默认是30秒，超时未完成任务则会抛出异常
        SseEmitter sseEmitter = new SseEmitter(0L);

        sseEmitter.onCompletion(completionCallback(userId));
        sseEmitter.onTimeout(timeoutCallback(userId));
        sseEmitter.onError(errorCallback(userId));

        sseClients.put(userId, sseEmitter);

        log.info("SSE链接成功，用户ID为：{}", userId);

        return sseEmitter;
    }

    /**
     * @param userId
     * @return java.lang.Runnable
     * @Description: SSE超时回调函数
     * @Date 2026/4/21 下午11:20
     * @Author liujxiao
     */
    public static Runnable timeoutCallback(String userId) {
        return () -> {
            // 移除用户连接
            remove(userId);
        };
    }

    /**
     * @param userId
     * @return java.lang.Runnable
     * @Description: SSE任务完成回调函数
     * @Date 2026/4/21 下午11:20
     * @Author liujxiao
     */
    public static Runnable completionCallback(String userId) {
        return () -> {
            // 移除用户连接
            remove(userId);
        };
    }

    /**
     * @param userId
     * @return java.util.function.Consumer<java.lang.Throwable>
     * @Description: SSE错误回调函数
     * @Date 2026/4/21 下午11:20
     * @Author liujxiao
     */
    public static Consumer<Throwable> errorCallback(String userId) {
        return Throwable -> {
            log.error("SSE出现异常..");
            // 移除用户
            remove(userId);
        };
    }

    /**
     * @param userId
     * @return void
     * @Description: 移除SSE服务
     * @Date 2026/4/21 下午11:21
     * @Author liujxiao
     */
    public static void remove(String userId) {
        sseClients.remove(userId);
        log.info("SSE链接被移除，移除的用户ID为：{}", userId);
    }

    /**
     * @Description: 发送SSE消息
     * @Date 2026/4/22 上午11:31
     * @Author liujxiao
     * @param userId
     * @param message
     * @param msgType
     * @return void
     */
    public static void sendMessage(String userId, String message, SSEMsgType msgType) {
        if (CollectionUtils.isEmpty(sseClients) || !sseClients.containsKey(userId)
            || sseClients.get(userId) == null) {
            // 无感知
            return;
        }
        SseEmitter sseEmitter = sseClients.get(userId);
        sendSseEmitterMessage(sseEmitter, userId, message, msgType);
    }

    /**
     * @param sseEmitter
     * @param userId
     * @param message
     * @param msgType
     * @return void
     * @Description: 消息发送业务
     * @Date 2026/4/22 上午11:26
     * @Author liujxiao
     */
    private static void sendSseEmitterMessage(SseEmitter sseEmitter,
        String userId, String message, SSEMsgType msgType) {
        try {
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                .id(userId)
                .data(message)
                .name(msgType.getType());

            sseEmitter.send(event);
        } catch (IOException e) {
            log.error("SSE异常", e);
            remove(userId);
        }
    }

}
