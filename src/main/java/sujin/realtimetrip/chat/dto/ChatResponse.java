package sujin.realtimetrip.chat.dto;

import lombok.Getter;
import lombok.Setter;
import sujin.realtimetrip.chat.enums.MessageType;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatResponse {
    private Long roomId;
    private Long userId;
    private String nickName;
    private String message;
    private MessageType type;
    private LocalDateTime timestamp;

    public ChatResponse(ChatRequest chatRequest) {
        this.roomId = chatRequest.getRoomId();
        this.userId = chatRequest.getUserId();
        this.message = chatRequest.getMessage();
        this.nickName = chatRequest.getNickName();
        this.type = chatRequest.getType();
        this.timestamp = LocalDateTime.now();
    }
}
