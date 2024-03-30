package sujin.realtimetrip.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //user
    DIFFERENT_VERIFY_CODE(HttpStatus.BAD_REQUEST, "1001", "이메일 인증번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
