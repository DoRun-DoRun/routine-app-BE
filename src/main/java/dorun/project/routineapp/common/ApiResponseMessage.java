package dorun.project.routineapp.common;

import lombok.Getter;

@Getter
public enum ApiResponseMessage {

    SUCCESS_REQUEST(200, "API 요청이 성공했습니다."),
    LOGIN_SUCCESS(200, "로그인에 성공했습니다."),
    TOKEN_REFRESH_SUCCESS(200, "토큰 재 발급에 성공했습니다."),
    INVALID_TOKEN(400, "토큰이 유효하지 않습니다."),
    INVALID_REQUEST(400, "잘못된 요청입니다."),
    UNAUTHORIZED_REQUEST(401, "인증에 실패한 요청입니다."),
    UNAUTHENTICATED_REQUEST(403, "인가에 실패한 요청입니다."),
    CREATE_ROUTINE(201, "루틴 생성에 성공했습니다."),
    SERVER_ERROR(500, "에러가 발생했습니다.");

    private final int code;
    private final String message;

    ApiResponseMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
