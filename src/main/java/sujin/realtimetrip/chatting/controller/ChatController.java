package sujin.realtimetrip.chatting.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sujin.realtimetrip.chatting.dto.ChatDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import sujin.realtimetrip.chatting.service.ChatService;


@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

    @MessageMapping("/enterUser")
    public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        chatService.plusUserCnt(chat.getRoomId());
        String userUUID = chatService.addUser(chat.getRoomId(), chat.getSender());

        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        chat.setMessage(chat.getSender() + " 님 입장!!");
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload ChatDto chat) {
        log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

    }
}
