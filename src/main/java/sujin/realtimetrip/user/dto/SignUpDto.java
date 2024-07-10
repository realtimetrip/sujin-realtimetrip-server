package sujin.realtimetrip.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SignUpDto {
    @NotNull(message = "이메일이 null 입니다.")
    @Email
    private String email;

    @NotNull(message = "비밀번호가 null 입니다.")
    private String password;

    @NotNull
    private String nickName;

    private MultipartFile profile;

}
