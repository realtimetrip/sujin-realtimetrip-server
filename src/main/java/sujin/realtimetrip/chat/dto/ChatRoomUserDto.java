package sujin.realtimetrip.chat.dto;

import lombok.Data;

@Data
public class ChatRoomUserDto {
    private String userId;
    private String nickName;
    private String profile;

    public ChatRoomUserDto() {}

    // 생성자
    public ChatRoomUserDto(String userId, String nickName, String profile) {
        this.userId = userId;
        this.nickName = nickName;
        this.profile = profile;
    }
}
