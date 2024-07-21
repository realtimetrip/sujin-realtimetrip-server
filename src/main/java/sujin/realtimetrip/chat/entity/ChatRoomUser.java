package sujin.realtimetrip.chat.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import sujin.realtimetrip.user.entity.User;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
@Table(name = "chat_user")
public class ChatRoomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "chatUser", cascade = CascadeType.REMOVE)
    private List<Chat> chats;


}