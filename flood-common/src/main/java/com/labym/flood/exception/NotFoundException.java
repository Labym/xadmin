package com.labym.flood.exception;

public class NotFoundException extends FloodException{

    public static final int BAD_REQUEST_CODE=400;
    public NotFoundException() {
        super(BAD_REQUEST_CODE);
    }

    public NotFoundException(String message) {
        super(400, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(BAD_REQUEST_CODE, message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(BAD_REQUEST_CODE, cause);
    }

    public NotFoundException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(BAD_REQUEST_CODE, message, cause, enableSuppression, writableStackTrace);
    }
}
