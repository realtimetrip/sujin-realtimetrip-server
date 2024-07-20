package sujin.realtimetrip.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sujin.realtimetrip.country.entity.Country;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(name = "user_count")
    private Long user_count;

    @Column(name = "last_chat_id")
    private Long lastChatId;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Chat> chats;

    @OneToOne
    @JoinColumn(name = "country_id") // 외래 키 지정
    private Country country;
}
