package com.security.api.advice.exception;

public class CResourceNotExistException extends RuntimeException{
    public CResourceNotExistException() {super();}

    public CResourceNotExistException(String message) {
        super(message);
    }

    public CResourceNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
