package sujin.realtimetrip.chat.dto;

import lombok.Getter;
import lombok.Setter;
import sujin.realtimetrip.chat.enums.MessageType;

@Getter
@Setter
public class ChatRequest {
    private String roomId;
    private Long userId;
    private String nickName;
    private String message;
    private MessageType type;
}