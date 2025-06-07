package shop.matddang.matddangbe.security.exception;


import shop.matddang.matddangbe.global.exception.CustomException;

public class TokenException extends CustomException {

    public TokenException(SecurityErrorCode errorCode) {
        super(errorCode);
    }

    public TokenException(SecurityErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
