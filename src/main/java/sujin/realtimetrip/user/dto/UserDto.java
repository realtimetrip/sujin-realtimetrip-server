package sujin.realtimetrip.user.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long userId;
    private String email;
    private String nickName;
    private String profile;

    public UserDto() {
    }

    public UserDto(Long userId, String email, String nickName, String profile) {
        this.userId = userId;
        this.email = email;
        this.nickName = nickName;
        this.profile = profile;
    }

}
