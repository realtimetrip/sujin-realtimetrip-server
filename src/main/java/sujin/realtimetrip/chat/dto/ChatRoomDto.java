package sujin.realtimetrip.chat.dto;

import lombok.Data;

@Data
public class ChatRoomDto {
    private String chatRoomId;
    private String countryName;
    private Long userCount;

    public ChatRoomDto() {}

    // 생성자
    public ChatRoomDto(String chatRoomId, String countryName, Long userCount) {
        this.chatRoomId = chatRoomId;
        this.countryName = countryName;
        this.userCount = userCount;
    }
}
