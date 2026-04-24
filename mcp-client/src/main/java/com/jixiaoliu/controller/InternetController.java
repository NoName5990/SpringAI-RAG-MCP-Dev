package com.jixiaoliu.controller;

import com.jixiaoliu.bean.ChatEntity;
import com.jixiaoliu.service.ChatService;
import com.jixiaoliu.service.SearxngService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName InternetController
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/24 下午4:13
 */
@RestController
@RequestMapping("internet")
public class InternetController {

    @Resource
    private SearxngService searxngService;
    @Resource
    private ChatService chatService;


    @GetMapping("/test")
    public Object test(@RequestParam String query) {
        return searxngService.websearch(query);
    }

    /**
     * @Description: searxng联网查询
     * @Date 2026/4/24 下午6:08
     * @Author liujxiao
     * @param chatEntity
     * @return void
     */
    @PostMapping("search")
    public void search(@RequestBody ChatEntity chatEntity) {
        chatService.doSearxngSearch(chatEntity);
    }
}
