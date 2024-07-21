package sujin.realtimetrip.chat.dto;

import lombok.Getter;
import lombok.Setter;
import sujin.realtimetrip.chat.enums.MessageType;

@Getter
@Setter
public class ChatRequest {
    private Long userId;
    private String chatRoomId;
    private String message;
    private MessageType type;
}