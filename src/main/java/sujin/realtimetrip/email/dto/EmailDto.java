package sujin.realtimetrip.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailDto {
    @Email
    @NotEmpty(message = "이메일을 입력해 주세요")
    private String email;
}