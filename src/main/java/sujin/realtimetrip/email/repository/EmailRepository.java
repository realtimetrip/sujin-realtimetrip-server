package sujin.realtimetrip.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sujin.realtimetrip.email.entity.AuthCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<AuthCode, Long> {

    void deleteByEmail(String email);

    List<AuthCode> findByExpiresAtBefore(LocalDateTime now);

    Optional<AuthCode> findByEmail(String email);
}
