package com.klasha.code.challenge.exception;

public class BadRequestException extends AbstractException{

    public BadRequestException(String code, String message) {
        super(code, message);
    }
}
