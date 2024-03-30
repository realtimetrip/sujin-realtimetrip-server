package sujin.realtimetrip.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //mail
    AUTH_CODE_VERIFICATION_FAILED(HttpStatus.BAD_REQUEST, "1001", "인증 번호 검증에 실패하였습니다. 다시 시도해 주세요."),

    //user
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "2001", "이미 사용 중인 이메일입니다. 다른 이메일을 사용해 주세요.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
