package com.mottu.mottu.exception;

public class MotoNotFoundException extends Exception{

    public MotoNotFoundException() {
    }

    public MotoNotFoundException(String message) {
        super(message);
    }

    public MotoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MotoNotFoundException(Throwable cause) {
        super(cause);
    }

    public MotoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
