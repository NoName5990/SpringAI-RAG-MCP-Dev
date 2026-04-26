package com.jixiaoliu.controller;

import com.jixiaoliu.bean.ChatEntity;
import com.jixiaoliu.common.CustomResult;
import com.jixiaoliu.service.ChatService;
import com.jixiaoliu.service.DocumentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName RagController
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/23 上午11:21
 */
@RestController
@RequestMapping("rag")
@RequiredArgsConstructor
public class RagController {

    private final DocumentService documentService;
    private final ChatService chatService;

    /**
     * @Description: 上传文档读取并存放到向量库
     * @Date 2026/4/23 下午2:23
     * @Author liujxiao
     * @param file
     * @return com.jixiaoliu.common.CustomResult
     */
    @PostMapping("uploadRagDoc")
    public CustomResult uploadRagDoc(@RequestParam("file") MultipartFile file) {
        Resource resource = file.getResource();
        String fileName = file.getOriginalFilename();
        List<Document> documents = documentService.loadText(resource, fileName);
        return CustomResult.ok(documents);
    }

    @GetMapping("doSearch")
    public CustomResult doSearch(@RequestParam("question") String question) {
        return CustomResult.ok(documentService.doSearch(question));
    }

    /**
     * @Description: rag查询
     * @Date 2026/4/24 下午6:05
     * @Author liujxiao
     * @param chatEntity
     * @return void
     */
    @PostMapping("search")
    public void search(@RequestBody ChatEntity chatEntity) {
        chatService.doRagSearch(chatEntity);
    }



}
