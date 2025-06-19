package shop.matddang.matddangbe.user.exception;


import shop.matddang.matddangbe.global.exception.CustomException;
import shop.matddang.matddangbe.global.exception.ErrorCode;

public class AuthenticationException extends CustomException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthenticationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
