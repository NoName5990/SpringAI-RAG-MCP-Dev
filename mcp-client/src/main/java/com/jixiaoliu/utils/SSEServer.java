package com.jixiaoliu.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
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

    private static final Map<String,SseEmitter> sseClients = new HashMap<String,SseEmitter>();

    /**
     * @Description: 连接SSE服务
     * @Date 2026/4/21 下午11:20
     * @Author liujxiao
     * @param userId
     * @return org.springframework.web.servlet.mvc.method.annotation.SseEmitter
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

        sseClients.put(userId,sseEmitter);

        log.info("SSE链接成功，用户ID为：{}", userId);

        return sseEmitter;
    }

    /**
     * @Description: SSE超时回调函数
     * @Date 2026/4/21 下午11:20
     * @Author liujxiao
     * @param userId
     * @return java.lang.Runnable
     */
    public static Runnable timeoutCallback(String userId) {
        return ()-> {
            // 移除用户连接
            remove(userId);
        };
    }

    /**
     * @Description: SSE任务完成回调函数
     * @Date 2026/4/21 下午11:20
     * @Author liujxiao
     * @param userId
     * @return java.lang.Runnable
     */
    public static Runnable completionCallback(String userId) {
        return ()-> {
            // 移除用户连接
            remove(userId);
        };
    }

    /**
     * @Description: SSE错误回调函数
     * @Date 2026/4/21 下午11:20
     * @Author liujxiao
     * @param userId
     * @return java.util.function.Consumer<java.lang.Throwable>
     */
    public static Consumer<Throwable> errorCallback(String userId) {
        return Throwable -> {
            log.error("SSE出现异常..");
            // 移除用户
            remove(userId);
        };
    }

    /**
     * @Description: 移除SSE服务
     * @Date 2026/4/21 下午11:21
     * @Author liujxiao
     * @param userId
     * @return void
     */
    public static void remove(String userId) {
        sseClients.remove(userId);
        log.info("SSE链接被移除，移除的用户ID为：{}", userId);
    }


}
