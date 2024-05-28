package dorun.project.routineapp.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "COMMON-001", "유효성 검증에 실패했습니다."),
    DUPLICATE_INPUT_VALUE(409, "COMMON-002", "이미 등록된 값이 들어왔습니다."),
    BAD_REQUEST(400, "COMMON-003", "잘못된 요청입니다."),
    ENTITY_NOT_FOUND(404, "COMMON-004", "엔티티를 찾을 수 없습니다."),

    UNAUTHORIZED(401, "USER-001", "인증에 실패했습니다."),
    USER_NOT_FOUND(404, "USER-002", "유저을 찾을 수 없습니다."),
    ROLE_NOT_EXISTS(403, "USER-003", "권한이 부족합니다."),
    TOKEN_NOT_EXISTS(404, "USER-004", "인증 토큰이 존재하지 않습니다."),
    DUPLICATE_LOGIN_ID(409, "USER-005", "유저 아이디가 중복됐습니다."),
    DUPLICATE_EMAIL(409, "USER-006", "이메일이 중복되었습니다. "),

    EXPIRED_VERIFICATION_TOKEN(403, "AUTH-001", "인증 토큰이 만료되었습니다."),
    INVALID_VERIFICATION_TOKEN(403, "AUTH-002", "토큰이 유효하지 않습니다."),
    CERTIFICATION_TYPE_NOT_MATCH(403, "AUTH-003", "인증 타입이 일치하지 않습니다."),


    ROUTINE_NOT_FOUND(404, "ROUTINE-001", "루틴을 찾을 수 없습니다."),
    ;


    int statusCode;
    String resultCode;
    String message;

    ErrorCode(int statusCode, String resultCode, String message) {
        this.statusCode = statusCode;
        this.resultCode = resultCode;
        this.message = message;
    }
}
