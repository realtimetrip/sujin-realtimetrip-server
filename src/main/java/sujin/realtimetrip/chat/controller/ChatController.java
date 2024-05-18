package sujin.realtimetrip.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sujin.realtimetrip.chat.dto.ChatRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import sujin.realtimetrip.chat.service.ChatService;


@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatController {

    private final ChatService chatService;

    // 채팅방 입장
    @MessageMapping("/chat/enter")
    public void enterUser(@Payload ChatRequest chatRequest) {
        chatService.enterUser(chatRequest);
    }

    // 채팅 보내기
    @MessageMapping("/chat/send")
    public void sendMessage(@Payload ChatRequest chatRequest) {
        chatService.sendMessage(chatRequest);

    }
}