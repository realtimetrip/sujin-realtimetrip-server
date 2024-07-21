package sujin.realtimetrip.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoomMessagesDto {
    private String chatRoomId;
    private String countryName;
    private List<ChatResponse> chatMessages;

    // Constructor, getters, and setters
    public ChatRoomMessagesDto(String chatRoomId, String countryName, List<ChatResponse> chatMessages) {
        this.chatRoomId = chatRoomId;
        this.countryName = countryName;
        this.chatMessages = chatMessages;
    }
}