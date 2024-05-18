package sujin.realtimetrip.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //mail
    AUTH_CODE_VERIFICATION_FAILED(HttpStatus.BAD_REQUEST, "1001", "인증 번호 검증에 실패하였습니다. 다시 시도해 주세요."),

    //user
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "2001", "이미 사용 중인 이메일입니다. 다른 이메일을 사용해 주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "2002", "해당 사용자를 찾을 수 없습니다."),

    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "2002", "이메일 또는 비밀번호가 올바르지 않습니다"),

    //chat
    CHAT_MESSAGE_NOT_FOUNT(HttpStatus.NOT_FOUND, "3001", "채팅 메시지를 찾을 수 없습니다."),
    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "3002", "채팅방을 찾을 수 없습니다."),
    CHAT_ROOM_ALREADY_EXISTS(HttpStatus.CONFLICT, "3003", "채팅방이 이미 존재합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}