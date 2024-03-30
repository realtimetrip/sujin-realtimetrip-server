package sujin.realtimetrip.Mail.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sujin.realtimetrip.User.entity.User;

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

    private int authNum;
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "authCode")
    private User user;

}
