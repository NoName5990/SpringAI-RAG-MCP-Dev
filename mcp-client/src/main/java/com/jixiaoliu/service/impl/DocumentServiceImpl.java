package com.jixiaoliu.service.impl;

import com.jixiaoliu.service.DocumentService;
import com.jixiaoliu.utils.CustomTextSplitter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @ClassName DocumentServiceImpl
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/23 上午11:24
 */
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final RedisVectorStore redisVectorStore;

    @Override
    public List<Document> loadText(Resource resource, String fileName) {
        // 文本读取器读取文本
        TextReader textContent = new TextReader(resource);
        textContent.getCustomMetadata().put("fileName", fileName);
        List<Document> documentList = textContent.get();
        // System.out.println("未分割："+documentList);

        // System.out.println("documentList: " + documentList);

        // 默认文档切割方式
        // TokenTextSplitter splitter = new TokenTextSplitter();
        // List<Document> list = splitter.apply(documentList);

        // 自定义文档切割方式
        CustomTextSplitter splitter = new CustomTextSplitter();
        List<Document> list = splitter.apply(documentList);

        // System.out.println("已分割："+list);

        // 向量存储
        redisVectorStore.add(list);

        return documentList;
    }

    @Override
    public List<Document> doSearch(String question) {

        return redisVectorStore.similaritySearch(question);

        // 对返回的答案进行过滤和选择
        /*SearchRequest searchRequest = SearchRequest.builder()
            .query(question)
            .topK(3)
            .similarityThreshold(0.7)
            .build(); // 相似度阈值，只返回相关度>=70%的结果
        return redisVectorStore.similaritySearch(searchRequest);*/
    }
}
