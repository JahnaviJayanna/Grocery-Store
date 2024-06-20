package io.swagger.exception;

public class UserNotActiveException extends ApiException{
    private int code;
    public UserNotActiveException(int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
