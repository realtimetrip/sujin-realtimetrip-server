package sujin.realtimetrip.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sujin.realtimetrip.chat.entity.ChatUser;

import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "nick_name")
    private String nickName;

    private String profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ChatUser> chatUsers;

    public User(String email, String password, String nickName){
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }
}
