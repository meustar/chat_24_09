package com.koreait.exam.chat_24_09;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ChatMessage {
    private long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/seoul")
    private LocalDateTime createDate;           // -> 2024-09-06T13:29:54.6490785
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