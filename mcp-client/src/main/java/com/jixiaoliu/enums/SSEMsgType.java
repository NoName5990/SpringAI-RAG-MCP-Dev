package com.jixiaoliu.enums;

import lombok.Getter;

/**
 * @ClassName SSEMsgType
 * @Author liujxiao
 * @Version 1.0
 * @Description SSE消息类型枚举类
 * @date 2026/4/22 上午11:05
 */
@Getter
public enum SSEMsgType {

    MESSAGE("message","单次发送的普通消息类型"),
    ADD("add","消息追加，适用于流式stream推送"),
    FINISH("finish","消息完成"),
    CUSTOM_EVENT("custom_event","单次发送的普通消息类型"),
    DONE("done","单次发送的普通消息类型");


    private final String type;
    private final String desc;

    SSEMsgType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
