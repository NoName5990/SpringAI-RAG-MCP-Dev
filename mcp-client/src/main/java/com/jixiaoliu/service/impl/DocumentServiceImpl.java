package com.jixiaoliu.service.impl;

import com.jixiaoliu.service.DocumentService;
import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
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
public class DocumentServiceImpl implements DocumentService {

    @Override
    public List<Document> loadText(Resource resource, String fileName) {
        // 文本读取器读取文本
        TextReader textContent = new TextReader(resource);
        textContent.getCustomMetadata().put("fileName", fileName);
        List<Document> documentList = textContent.get();
        System.out.println("documentList: " + documentList);
        return documentList;
    }
}
