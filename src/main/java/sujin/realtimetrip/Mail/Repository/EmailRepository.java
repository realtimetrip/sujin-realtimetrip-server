package sujin.realtimetrip.Mail.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sujin.realtimetrip.Mail.Entity.AuthCode;

@Repository
public interface EmailRepository extends JpaRepository<AuthCode, Long> {

}
