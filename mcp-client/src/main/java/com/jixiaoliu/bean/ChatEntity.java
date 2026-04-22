package com.jixiaoliu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ChatEntity
 * @Author liujxiao
 * @Version 1.0
 * @Description ChatEntity
 * @date 2026/4/22 下午10:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity {
    private String currentUserName;
    private String message;
    private String botMsgId;
}
