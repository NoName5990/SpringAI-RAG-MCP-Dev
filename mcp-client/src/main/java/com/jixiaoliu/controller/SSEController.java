package com.jixiaoliu.controller;

import com.jixiaoliu.enums.SSEMsgType;
import com.jixiaoliu.utils.SSEServer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @ClassName SSEController
 * @Author liujxiao
 * @Version 1.0
 * @Description SSEServer
 * @date 2026/4/22 上午10:29
 */
@RestController
@RequestMapping("sse")
public class SSEController {

    /**
     * @Description: SSE连接
     * @Date 2026/4/22 上午10:32
     * @Author liujxiao
     * @param userId
     * @return org.springframework.web.servlet.mvc.method.annotation.SseEmitter
     */
    @GetMapping(value = "connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@RequestParam String userId) {
        return SSEServer.connect(userId);
    }

    /**
     * @Description: SSE向用户发送消息
     * @Date 2026/4/22 下午10:01
     * @Author liujxiao
     * @param userId
     * @param message
     * @return java.lang.Object
     */
    @GetMapping( "sendMessage")
    public Object sendMessage(@RequestParam String userId, @RequestParam String message) {
        // e1iuia6e6
        SSEServer.sendMessage(userId, message, SSEMsgType.MESSAGE);
        return "SUCCESS";
    }

    /**
     * @Description: SSE群发消息
     * @Date 2026/4/22 下午10:02
     * @Author liujxiao
     * @param message
     * @return java.lang.Object
     */
    @GetMapping( "sendMessageAll")
    public Object sendMessageAll(@RequestParam String message) {
        SSEServer.sendMessageToAllUser(message);
        return "SUCCESS";
    }
}
