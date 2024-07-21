package sujin.realtimetrip.chat.dto;

import lombok.Getter;
import lombok.Setter;
import sujin.realtimetrip.chat.enums.MessageType;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatResponse {
    private Long chatId;
    private String chatRoomId;
    private Long userId;
    private String nickName;
    private String message;
    private LocalDateTime eventTime;
    private MessageType type;

    public ChatResponse(Long chatId, ChatRequest chatRequest, String nickName) {
        this.chatId = chatId;
        this.chatRoomId = chatRequest.getChatRoomId();
        this.userId = chatRequest.getUserId();
        this.nickName = nickName;
        this.message = chatRequest.getMessage();
        this.eventTime = LocalDateTime.now();
        this.type = chatRequest.getType();
    }
}
