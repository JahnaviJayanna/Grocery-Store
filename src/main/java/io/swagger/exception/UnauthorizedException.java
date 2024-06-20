package io.swagger.exception;

public class UnauthorizedException extends ApiException{
    private int code;
    public UnauthorizedException(int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
