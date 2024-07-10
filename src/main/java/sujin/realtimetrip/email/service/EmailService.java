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
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;
import sujin.realtimetrip.util.RedisUtil;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {

    private final EmailRepository emailRepository;

    // 메일 전송할 때 사용
    private final JavaMailSender emailSender;

    // 타임리프를 이용한 context 설정
    private final SpringTemplateEngine templateEngine;

    //RedisUtil 주입
    private final RedisUtil redisUtil;

    // controller - 인증 번호 이메일 전송 (DB에 인증 번호 저장)
    public String mailSend(String email) throws MessagingException {
        // 랜덤 인증 번호 생성
        String authCode = createCode();
        // DB에 이메일 및 인증 번호 저장
        AuthCode authcode = new AuthCode(email, authCode);
        emailRepository.saveAndFlush(authcode);
        // 메일 양식 작성
        MimeMessage emailForm = createEmailForm(email, authCode);
        // 메일 전송
        emailSender.send(emailForm);

        return authCode;
    }

    // controller - 인증 번호 이메일 전송 (레디스에 인증 번호 저장)
    public void redisMailSend(String email) throws MessagingException {
        // 인증 번호 생성
        String redisAuthCode = createCode();
        // 메일 양식 작성
        MimeMessage emailForm = createEmailForm(email, redisAuthCode);
        // 메일 전송
        emailSender.send(emailForm);
        // 레디스에 email 및 인증 번호 저장, 유효기간 5분
        redisUtil.setDataExpire(email, redisAuthCode,60*5L);
    }

    // 랜덤 인증 번호 생성
    public String createCode() {
        SecureRandom secureRandom = new SecureRandom();

        // 1000부터 9999까지의 랜덤한 숫자 생성
        int code = secureRandom.nextInt(9000) + 1000;

        return String.valueOf(code);
    }

    // 메일 양식 작성
    public MimeMessage createEmailForm(String email, String authCode) throws MessagingException {
        String title = "회원가입 이메일 인증"; //제목
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); // 받는 사람 이메일 설정
        message.setSubject(title); // 제목 설정
        message.setText(setContext(authCode), "utf-8", "html");

        return message;
    }

    // 타임리프를 이용한 context 설정
    public String setContext(String authCode) {
        Context context = new Context();
        context.setVariable("code", authCode); // 생성된 인증 번호가 th:text="${code}와 매핑
        return templateEngine.process("verification-email", context); // verification-email.html
    }

    // 유저가 여러번 인증 번호를 보낸 경우 기존 인증번호 삭제
    public void deleteExistCode(String email){
        emailRepository.deleteByEmail(email);
    }

    // controller - 이메일 인증 번호 검증 (DB에서 인증 번호 검증)
    public Boolean verifyAuthCode(String email, String authCode) {
        Optional<AuthCode> authCodeOptional = emailRepository.findByEmail(email);

        // 이메일로 인증 번호를 찾았는지, 인증 번호가 일치하는지 검사
        if (authCodeOptional.isEmpty() ||
                !authCodeOptional.get().getAuthCode().equals(authCode)) {
            // 구체적인 오류 원인을 제공하지 않고, 일반적인 오류 메시지를 사용자에게 반환
            throw new CustomException(ErrorCode.AUTH_CODE_VERIFICATION_FAILED);
        }

        // 인증 코드가 일치하고, 만료되지 않았으면 true 반환
        return true;
    }

    // controller - 이메일 인증 번호 검증 (레디스에서 인증 번호 검증
    public Boolean redisVerifyAuthCode(String email, String authCode) {
        // 이메일로 인증 코드를 찾았는지, 인증 코드가 일치하는지 검사
        if(redisUtil.getData(email)==null || !redisUtil.getData(email).equals(authCode)){
            throw new CustomException(ErrorCode.AUTH_CODE_VERIFICATION_FAILED);
        }
        else{
            // 인증 코드가 일치하고, 만료되지 않았으면 true 반환
            return true;
        }
    }
}
