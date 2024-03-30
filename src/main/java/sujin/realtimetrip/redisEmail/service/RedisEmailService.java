package sujin.realtimetrip.redisEmail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;
import sujin.realtimetrip.util.RedisUtil;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class RedisEmailService {

    // 타임리프를 이용한 context 설정
    private final SpringTemplateEngine templateEngine;
    // 메일 전송할 때 사용
    private final JavaMailSender emailSender;
    //RedisUtil을 주입
    private final RedisUtil redisUtil;

    private String authCode;

    public String sendEmailAuthCode(String email) throws MessagingException {
        // 메일 양식 작성
        MimeMessage emailForm = createEmailForm(email);

        // 메일 전송
        emailSender.send(emailForm);
        redisUtil.setDataExpire(email, authCode,60*5L);

        return "이메일 인증 번호 전송을 성공했습니다.";
    }

    // 랜덤 인증 코드 생성
    public String createCode(String email) {
        // 랜덤 인증 코드 생성
        Random random = new Random();
        authCode = String.valueOf(random.nextInt(9000)+1000); // 범위: 1000 ~ 9999

        return authCode;
    }

    public MimeMessage createEmailForm(String email) throws MessagingException {

        authCode = createCode(email); // 인증 코드 생성
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

    public Boolean CheckAuthNum(String email, String authCode) {
        if(redisUtil.getData(email)==null || !redisUtil.getData(email).equals(authCode)){
            throw new CustomException(ErrorCode.AUTH_CODE_VERIFICATION_FAILED);
        }
        else{
            return true;
        }
    }
}
