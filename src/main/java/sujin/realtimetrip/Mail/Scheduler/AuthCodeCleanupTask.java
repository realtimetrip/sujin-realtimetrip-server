package sujin.realtimetrip.Mail.Scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sujin.realtimetrip.Mail.Entity.AuthCode;
import sujin.realtimetrip.Mail.Repository.EmailRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthCodeCleanupTask {
    private final EmailRepository emailRepository;

    // 매분 마다 만료된 인증 번호 삭제
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void deleteExpiredAuthNum() {
        LocalDateTime now = LocalDateTime.now();
        List<AuthCode> expiredCodes = emailRepository.findByExpiresAtBefore(now);
        emailRepository.deleteAll(expiredCodes);
    }
}
