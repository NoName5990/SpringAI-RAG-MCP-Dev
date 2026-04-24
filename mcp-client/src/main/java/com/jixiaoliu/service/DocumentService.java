package com.jixiaoliu.service;

import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName DocumentService
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/23 上午11:22
 */
public interface DocumentService {

    /**
     * @Description: 读取并加载文档，最后存入向量库
     * @Date 2026/4/23 下午2:26
     * @Author liujxiao
     * @param resource
     * @param fileName
     * @return java.util.List<org.springframework.ai.document.Document>
     */
    public List<Document> loadText(Resource resource, String fileName);

    /**
     * @Description: 查询向量库
     * @Date 2026/4/23 下午2:26
     * @Author liujxiao
     * @param question
     * @return java.util.List<org.springframework.ai.document.Document>
     */
    List<Document> doSearch(String question);
}
