package sujin.realtimetrip.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sujin.realtimetrip.chat.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

}
