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

    @GetMapping( "sendMessage")
    public Object connect(@RequestParam String userId, @RequestParam String message) {
        // e1iuia6e6
        SSEServer.sendMessage(userId, message, SSEMsgType.MESSAGE);
        return "SUCCESS";
    }
}
