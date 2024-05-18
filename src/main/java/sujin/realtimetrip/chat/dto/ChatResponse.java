package sujin.realtimetrip.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatResponse {
    private String roomId;
    private Long userId;
    private String nickName;
    private String message;
    private LocalDateTime timestamp;

    public ChatResponse(ChatRequest chatRequest) {
        this.roomId = chatRequest.getRoomId();
        this.userId = chatRequest.getUserId();
        this.message = chatRequest.getMessage();
        this.nickName = chatRequest.getNickName();
        this.timestamp = LocalDateTime.now();
    }
}
