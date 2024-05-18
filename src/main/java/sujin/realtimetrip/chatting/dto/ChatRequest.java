package sujin.realtimetrip.chatting.dto;

import lombok.Getter;
import lombok.Setter;
import sujin.realtimetrip.chatting.enums.MessageType;

@Getter
@Setter
public class ChatRequest {
    private String roomId;
    private Long userId;
    private String nickName;
    private String message;
    private MessageType type;
}