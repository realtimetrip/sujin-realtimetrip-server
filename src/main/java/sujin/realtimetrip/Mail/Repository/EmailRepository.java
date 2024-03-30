package sujin.realtimetrip.Mail.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sujin.realtimetrip.Mail.Entity.AuthCode;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<AuthCode, Long> {

    void deleteByEmail(String email);

    List<AuthCode> findByExpiresAtBefore(LocalDateTime now);
}
