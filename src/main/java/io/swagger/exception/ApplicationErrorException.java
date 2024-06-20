package io.swagger.exception;

public class ApplicationErrorException extends ApiException{
    private int code;
    public ApplicationErrorException(int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
