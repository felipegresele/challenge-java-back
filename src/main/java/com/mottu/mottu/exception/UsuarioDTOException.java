package com.mottu.mottu.exception;

public class UsuarioDTOException extends Exception{

    public UsuarioDTOException() {
    }

    public UsuarioDTOException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioDTOException(String message) {
        super(message);
    }

    public UsuarioDTOException(Throwable cause) {
        super(cause);
    }

    public UsuarioDTOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
