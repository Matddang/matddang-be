package shop.matddang.matddangbe.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import shop.matddang.matddangbe.global.exception.ErrorCode;

@RequiredArgsConstructor
@Getter
public enum PlaceErrorCode implements ErrorCode {

    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "장소를 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "해당 장소에 접근할 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
