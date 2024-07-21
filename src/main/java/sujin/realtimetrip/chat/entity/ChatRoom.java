package sujin.realtimetrip.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sujin.realtimetrip.country.entity.Country;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @Column(name = "chat_room_id", updatable = false, nullable = false)
    private String id;

    @Column(name = "user_count")
    private Long userCount;

    @Column(name = "last_chat_id")
    private Long lastChatId;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Chat> chats;

    @OneToOne
    @JoinColumn(name = "country_id") // 외래 키 지정
    private Country country;

    public ChatRoom(Country country) {
        this.id = UUID.randomUUID().toString();
        this.userCount = 0L;
        this.lastChatId = 0L;
        this.country = country;
    }
}
