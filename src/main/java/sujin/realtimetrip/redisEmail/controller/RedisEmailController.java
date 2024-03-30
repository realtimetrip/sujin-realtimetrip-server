package sujin.realtimetrip.redisEmail.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sujin.realtimetrip.email.dto.EmailAuthDto;
import sujin.realtimetrip.email.dto.EmailDto;
import sujin.realtimetrip.redisEmail.service.RedisEmailService;
import sujin.realtimetrip.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/redis-email")
public class RedisEmailController {

    private final RedisEmailService redisEmailService;

    // 인증 번호 이메일 전송
    @PostMapping("/send-authCode")
    public ResponseEntity<ApiResponse<String>> mailSend(@RequestBody @Valid EmailDto emailDto) throws MessagingException {
        return ResponseEntity.ok().body(ApiResponse.success(redisEmailService.sendEmailAuthCode(emailDto.getEmail())));
    }

    @PostMapping("/check-authCode")
    public ResponseEntity<ApiResponse<Boolean>> checkAuthCode(@RequestBody @Valid EmailAuthDto emailAuthDto){
        return ResponseEntity.ok().body(ApiResponse.success(redisEmailService.CheckAuthNum(emailAuthDto.getEmail(),emailAuthDto.getAuthCode())));
    }

}
