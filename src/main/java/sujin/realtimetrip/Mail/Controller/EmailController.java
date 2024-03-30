package sujin.realtimetrip.Mail.Controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sujin.realtimetrip.Mail.Dto.EmailDto;
import sujin.realtimetrip.Mail.Service.EmailService;
import sujin.realtimetrip.util.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {

    private final EmailService emailService;

    // 인증 번호 이메일 전송
    @PostMapping("/send-authcode")
    public ResponseEntity<ApiResponse<String>> mailConfirm(@RequestBody EmailDto emailDto) throws MessagingException {

        // 이메일 인증을 두 번한 경우 이전 인증번호 삭제
        emailService.deleteExistCode(emailDto.getEmail());

        return ResponseEntity.ok().body(ApiResponse.success(emailService.sendEmailAuthCode(emailDto.getEmail())));
    }
}
