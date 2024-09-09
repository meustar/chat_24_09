package com.koreait.exam.chat_24_09;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/chat")
@Slf4j
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

    @GetMapping("/room")
    public String showRoom() {
        return "chat/room";
    }


    @PostMapping("/writeMessage")
    @ResponseBody
    public RsData<writeMessageResponse> writeMessage(@RequestBody writeMessageRequest req) {

        ChatMessage message = new ChatMessage(req.authorName, req.content);

        chatMessages.add(message);

        return new RsData<>("S-1", "메세지가 작성되었습니다.", new writeMessageResponse(message.getId()));
    }


    public record messagesRequest(Long fromId) {
    }

    public record messagesResponse(List<ChatMessage> chatMessages, long count) {
    }

    @GetMapping("/message")
    @ResponseBody
    public RsData<messagesResponse> message(messagesRequest req) {

        List<ChatMessage> messages = chatMessages;

        log.debug("req : {}", req);

        // 번호가 같이 입력 되었다면?
        if (req.fromId != null) {           // 글을 몇번째에 작성 했는지?
            // 해당 번호의 채팅 메세지가 전체 리스트의 몇번째 인덱스인지? 체크. 없다면 -1.
            int index = IntStream.range(0, messages.size())
                    .filter(i -> chatMessages.get(i).getId() == req.fromId) //filter 거름망, 원하는것만 거르는 느낌
                    .findFirst()             // 있다면 (여기까지)
                    .orElse(-1);    // 없다면.
            if (index != -1) {              // 글이 있다면.
                // 만약에 index가 -1이 아니라면? 0번부터 index번 까지 제거한 리스트를 만든다.
                messages = messages.subList(index + 1, messages.size());
            }
        }

        return new RsData<>("S-1", "성공", new messagesResponse(messages, messages.size()));
    }
}
