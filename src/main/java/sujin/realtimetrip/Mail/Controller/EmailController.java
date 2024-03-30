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
        return ResponseEntity.ok().body(ApiResponse.success(emailService.sendEmailAuthCode(emailDto.getEmail())));
    }
}
