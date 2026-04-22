package com.jixiaoliu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ChatResponseEntity
 * @Author liujxiao
 * @Version 1.0
 * @Description
 * @date 2026/4/22 下午11:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseEntity {
    private String message;
    private String botMsgId;
}
