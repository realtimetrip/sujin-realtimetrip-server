package sujin.realtimetrip.email.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sujin.realtimetrip.email.entity.AuthCode;
import sujin.realtimetrip.email.repository.EmailRepository;

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
