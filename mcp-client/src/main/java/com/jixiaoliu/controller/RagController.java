package com.jixiaoliu.controller;

import com.jixiaoliu.service.DocumentService;
import com.jixiaoliu.utils.CustomResult;
import com.jixiaoliu.utils.Resp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("uploadRagDoc")
    public CustomResult uploadRagDoc(@RequestParam("file") MultipartFile file) {
        Resource resource = file.getResource();
        String fileName = file.getOriginalFilename();
        List<Document> documents = documentService.loadText(resource, fileName);
        return CustomResult.ok(documents);
    }
}
