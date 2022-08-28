package com.guilhermepalma.springsecurityexample.dto.exceptions;

public class ConflictException extends Exception {

    public ConflictException(String text, Object... attributes) {
        super(String.format(text, attributes));
    }

}
