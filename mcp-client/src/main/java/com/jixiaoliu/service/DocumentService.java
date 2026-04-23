package com.jixiaoliu.service;

import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;

/**
 * @ClassName DocumentService
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/23 上午11:22
 */
public interface DocumentService {
    public List<Document> loadText(Resource resource, String fileName);
}
