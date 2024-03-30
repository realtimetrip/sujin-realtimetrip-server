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
@RequestMapping("/mail")
public class EmailController {

    private final EmailService emailService;

    // 인증 번호 이메일 전송
    @PostMapping("/send-authcode")
    public ResponseEntity<ApiResponse<String>> mailConfirm(@RequestBody @Valid EmailDto emailDto) throws MessagingException {

        // 이메일 인증을 두 번한 경우 이전 인증번호 삭제
        emailService.deleteExistCode(emailDto.getEmail());

        return ResponseEntity.ok().body(ApiResponse.success(emailService.sendEmailAuthCode(emailDto.getEmail())));
    }

    // 이메일 인증 번호 검사
    @PostMapping("/check-authcode")
    public ResponseEntity<ApiResponse<Boolean>> checkAuthCode(@RequestBody @Valid EmailAuthDto emailAuthDto){
        return ResponseEntity.ok().body(ApiResponse.success(emailService.verifyAuthCode(emailAuthDto.getEmail(), emailAuthDto.getAuthCode())));

    }
}
