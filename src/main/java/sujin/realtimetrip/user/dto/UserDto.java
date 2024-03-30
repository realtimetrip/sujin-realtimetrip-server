package sujin.realtimetrip.user.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long userId;
    private String email;
    private String nickName;

    public UserDto() {
    }

    public UserDto(Long userId, String email, String nickName) {
        this.userId = userId;
        this.email = email;
        this.nickName = nickName;
    }

}
