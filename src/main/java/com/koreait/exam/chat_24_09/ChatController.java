package com.koreait.exam.chat_24_09;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private List<ChatMessage> chatMessages = new ArrayList<>();


//    @AllArgsConstructor
//    @Getter
//    public static class writeMessageRequest {
//        private final String authorName;
//        private final String content;
//    }

    public record writeMessageRequest(String authorName, String content) {
    }

    public record writeMessageResponse(long id) {
    }

    public record messagesResponse(List<ChatMessage> chatMessages, long count) {
    }

    @PostMapping("/writeMessage")
    @ResponseBody
    public RsData<writeMessageResponse> writeMessage(@RequestBody writeMessageRequest req) {

        ChatMessage message = new ChatMessage(req.authorName, req.content);

        chatMessages.add(message);

        return new RsData<>("S-1", "메세지가 작성되었습니다.", new writeMessageResponse(message.getId()));
    }

    @GetMapping("/message")
    @ResponseBody
    public RsData<messagesResponse> message() {

        return new RsData<>("S-1", "메세지가 작성되었습니다.", new messagesResponse(chatMessages, chatMessages.size()));
    }
}
