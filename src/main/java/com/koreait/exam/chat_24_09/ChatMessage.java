package com.koreait.exam.chat_24_09;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ChatMessage {
    private long id;
    private LocalDateTime createDate;
    private String autohorName; // 작성자
    private String content;     // 작성글

    public ChatMessage(String autohorName, String content) {
        this(ChatMessageIdGenerator.getNextId(), LocalDateTime.now(), autohorName, content);     // constructor poll? pull? -> 다른 생성자 실행
    }
}

class ChatMessageIdGenerator {
    private static long id = 0;

    public static long getNextId() {
        return ++id;
    }
}