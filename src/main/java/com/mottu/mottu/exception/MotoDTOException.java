package com.mottu.mottu.exception;

public class MotoDTOException extends Exception{

    public MotoDTOException() {
    }

    public MotoDTOException(String message) {
        super(message);
    }

    public MotoDTOException(String message, Throwable cause) {
        super(message, cause);
    }

    public MotoDTOException(Throwable cause) {
        super(cause);
    }

    public MotoDTOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
