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
    CHAT_ROOM_ALREADY_EXISTS(HttpStatus.CONFLICT, "3003", "채팅방이 이미 존재합니다."),
    USER_ALREADY_IN_CHAT_ROOM(HttpStatus.BAD_REQUEST,"3004", "이미 채팅방에 입장한 유저입니다."),

    // AWS S3 image upload
    FILE_NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "4001", "파일 이름은 null일 수 없습니다."),
    ERROR_SAVING_IMAGE_TO_S3(HttpStatus.INTERNAL_SERVER_ERROR, "4002", "AWS S3에 이미지를 저장하는 동안 오류가 발생했습니다."),

    //country
    COUNTRY_CODE_DUPLICATED(HttpStatus.CONFLICT, "5001", "이미 등록된 나라입니다."),
    COUNTRY_NOT_FOUND(HttpStatus.NOT_FOUND, "5002", "해당 나라를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}