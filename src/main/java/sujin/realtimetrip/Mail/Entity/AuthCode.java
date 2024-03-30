package sujin.realtimetrip.Mail.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authCodeId;

    private String email;
    private String authCode;
    private LocalDateTime expiresAt;

    public AuthCode(String email, String authCode){
        this.email = email;
        this.authCode = authCode;
        this.expiresAt = LocalDateTime.now().plusMinutes(5); // 유효기간 5분으로 설정
    }

}
