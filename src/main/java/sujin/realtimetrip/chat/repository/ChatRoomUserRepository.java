package sujin.realtimetrip.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sujin.realtimetrip.chat.entity.ChatRoomUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

    Optional<ChatRoomUser> findByChatRoomIdAndUserId(String roomId, Long userId);

    List<ChatRoomUser> findByChatRoomId(String chatRoomId);
}
