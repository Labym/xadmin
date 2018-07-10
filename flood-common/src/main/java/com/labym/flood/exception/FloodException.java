package com.labym.flood.exception;

public class FloodException extends RuntimeException {
    private int status;

    public FloodException(int status) {
        this.status = status;
    }

    public FloodException(int status, String message) {
        super(message);
        this.status = status;
    }

    public FloodException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public FloodException(int status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public FloodException(int status, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }
}
