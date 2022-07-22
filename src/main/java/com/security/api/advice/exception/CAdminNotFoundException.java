package com.security.api.advice.exception;

public class CAdminNotFoundException extends RuntimeException {
    public CAdminNotFoundException() {super();}

    public CAdminNotFoundException(String message) {
        super(message);
    }

    public CAdminNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
