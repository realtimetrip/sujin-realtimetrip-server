package sujin.realtimetrip.email.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sujin.realtimetrip.email.dto.EmailAuthDto;
import sujin.realtimetrip.email.dto.EmailDto;
import sujin.realtimetrip.email.service.EmailService;
import sujin.realtimetrip.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    // 인증 번호 이메일 전송 (DB에 인증 번호 저장)
    @PostMapping("/send-authCode")
    public ResponseEntity<ApiResponse<String>> mailSend(@RequestBody @Valid EmailDto emailDto) throws MessagingException {
        // 이메일 인증을 두 번한 경우 이전 인증 번호 삭제
        emailService.deleteExistCode(emailDto.getEmail());
        return ResponseEntity.ok().body(ApiResponse.success(emailService.mailSend(emailDto.getEmail())));
    }

    // 이메일 인증 번호 검증 (DB에 저장된 인증 번호 검증)
    @PostMapping("/verify-authCode")
    public ResponseEntity<ApiResponse<Boolean>> verifyAuthCode(@RequestBody @Valid EmailAuthDto emailAuthDto){
        return ResponseEntity.ok().body(ApiResponse.success(emailService.verifyAuthCode(emailAuthDto.getEmail(), emailAuthDto.getAuthCode())));

    }

    // 인증 번호 이메일 전송 (레디스로 인증 번호 저장)
    @PostMapping("/send-authCode/with-redis")
    public ResponseEntity<ApiResponse<String>> redisMailSend(@RequestBody @Valid EmailDto emailDto) throws MessagingException {
        return ResponseEntity.ok().body(ApiResponse.success(emailService.redisMailSend(emailDto.getEmail())));
    }

    // 이메일 인증 번호 검증 (레디스에 저장된 인증 번호 검증)
    @PostMapping("/verify-authCode/with-redis")
    public ResponseEntity<ApiResponse<Boolean>> redisVerifyAuthCode(@RequestBody @Valid EmailAuthDto emailAuthDto){
        return ResponseEntity.ok().body(ApiResponse.success(emailService.redisVerifyAuthCode(emailAuthDto.getEmail(),emailAuthDto.getAuthCode())));
    }

}
