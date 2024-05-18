package sujin.realtimetrip.chatting.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import sujin.realtimetrip.chatting.dto.ChatRequest;
import sujin.realtimetrip.chatting.service.ChatService;


@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

    @MessageMapping("/enterUser")
    public void enterUser(@Payload ChatRequest chatRequest, SimpMessageHeaderAccessor headerAccessor) {
        chatService.plusUserCnt(chatRequest.getRoomId());
        String userUUID = chatService.addUser(chatRequest.getRoomId(), chatRequest.getNickName());

        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", chatRequest.getRoomId());

        chatRequest.setMessage(chatRequest.getNickName() + " 님 입장!!");
        template.convertAndSend("/sub/chat/room/" + chatRequest.getRoomId(), chatRequest);
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload ChatRequest chatRequest) {
        chatRequest.setMessage(chatRequest.getMessage());
        template.convertAndSend("/sub/chat/room/" + chatRequest.getRoomId(), chatRequest);

    }
}