package shop.matddang.matddangbe.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import shop.matddang.matddangbe.global.exception.ErrorCode;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum AuthenticationErrorCode implements ErrorCode {

    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_CREDENTIALS(UNAUTHORIZED, "잘못된 인증 정보입니다."),
    ACCOUNT_LOCKED(FORBIDDEN, "계정이 잠겼습니다. 관리자에게 문의하세요."),
    ACCOUNT_DISABLED(FORBIDDEN, "계정이 비활성화되었습니다. 관리자에게 문의하세요."),
    GOOGLE_AUTHENTICATION_FAILED(UNAUTHORIZED, "구글 인증에 실패했습니다. 인가코드가 유효하지 않거나 만료되었을 수 있습니다."),
    KAKAO_AUTHENTICATION_FAILED(UNAUTHORIZED, "카카오 인증에 실패했습니다. 인가코드가 유효하지 않거나 만료되었을 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
