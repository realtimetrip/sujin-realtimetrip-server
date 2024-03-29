package sujin.realtimetrip.User.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Entity
@Getter
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

    public User(){

    }

    public User(String email, String password, String nickName){
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }
}
