package sujin.realtimetrip.Mail.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sujin.realtimetrip.User.entity.User;

import java.time.LocalDate;
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
    private LocalDateTime createdAt;

    public AuthCode(String email, String authCode){
        this.email = email;
        this.authCode = authCode;
        this.createdAt = LocalDateTime.now();;
    }

}
