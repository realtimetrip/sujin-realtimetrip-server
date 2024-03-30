package sujin.realtimetrip.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;
import sujin.realtimetrip.email.entity.AuthCode;
import sujin.realtimetrip.email.repository.EmailRepository;
import sujin.realtimetrip.util.exception.CustomException;
import sujin.realtimetrip.util.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {

    private final EmailRepository emailRepository;

    // 메일 전송할 때 사용
    private final JavaMailSender emailSender;

    // 타임리프를 이용한 context 설정
    private final SpringTemplateEngine templateEngine;

    // 인증 번호 이메일 전송
    public String sendEmailAuthCode(String email) throws MessagingException {

        // 메일 양식 작성
        MimeMessage emailForm = createEmailForm(email);

        // 메일 전송
        emailSender.send(emailForm);

        return "이메일 인증 번호 전송을 성공했습니다.";
    }

    // 메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException {

        String authCode = createCode(email); // 인증 코드 생성
        String title = "회원가입 이메일 인증"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); // 받는 사람 이메일 설정
        message.setSubject(title); // 제목 설정
        message.setText(setContext(authCode), "utf-8", "html");

        return message;
    }

    // 랜덤 인증 코드 생성
    public String createCode(String email) {
        // 랜덤 인증 코드 생성
        Random random = new Random();
        String authCode = String.valueOf(random.nextInt(9000)+1000); // 범위: 1000 ~ 9999

        // 이메일 및 랜덤 인증 코드 저장
        AuthCode authcode = new AuthCode(email, authCode);
        emailRepository.saveAndFlush(authcode);

        return authCode;
    }

    // 타임리프를 이용한 context 설정
    public String setContext(String authCode) {
        Context context = new Context();
        context.setVariable("code", authCode); // 생성된 인증 번호가 th:text="${code}와 매핑
        return templateEngine.process("verification-email", context); // verification-email.html
    }

    // 유저가 여러번 인증 코드를 보낸 경우 기존 인증번호 삭제
    public void deleteExistCode(String email){
        emailRepository.deleteByEmail(email);
    }

    // 이메일 인증 번호 검사
    public Boolean verifyAuthCode(String email, String authCode) {
        Optional<AuthCode> authCodeOptional = emailRepository.findByEmail(email);

        // 이메일로 인증 번호를 찾았는지, 인증 코드의 만료 시간이 현재 시간보다 이전인지, 인증 코드가 일치하는지 검사
        if (authCodeOptional.isEmpty() ||
                authCodeOptional.get().getExpiresAt().isBefore(LocalDateTime.now()) ||
                !authCodeOptional.get().getAuthCode().equals(authCode)) {

            // 구체적인 오류 원인을 제공하지 않고, 일반적인 오류 메시지를 사용자에게 반환
            throw new CustomException(ErrorCode.AUTH_CODE_VERIFICATION_FAILED);
        }

        // 인증 코드가 일치하고, 만료되지 않았으면 true 반환
        return true;
    }
}
