package com.labym.flood.exception;

public class BadRequestException  extends FloodException{

    public static final int BAD_REQUEST_CODE=400;
    public BadRequestException() {
        super(BAD_REQUEST_CODE);
    }

    public BadRequestException(String message) {
        super(400, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(BAD_REQUEST_CODE, message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(BAD_REQUEST_CODE, cause);
    }

    public BadRequestException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(BAD_REQUEST_CODE, message, cause, enableSuppression, writableStackTrace);
    }
}
