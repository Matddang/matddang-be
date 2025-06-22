package shop.matddang.matddangbe.user.exception;

import shop.matddang.matddangbe.global.exception.CustomException;
import shop.matddang.matddangbe.global.exception.ErrorCode;

public class PlaceException extends CustomException {
    public PlaceException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PlaceException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
