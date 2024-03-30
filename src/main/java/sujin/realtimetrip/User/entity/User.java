package sujin.realtimetrip.User.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sujin.realtimetrip.Mail.Entity.AuthCode;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(unique = true)
    private String email;

    private String password;

    private String nickName;

    private String profile;

    // AuthCode 엔티티와 1:1 관계 설정
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auth_code_id", referencedColumnName = "id")
    private AuthCode authCode;

    public User(String email, String password, String nickName){
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }
}
