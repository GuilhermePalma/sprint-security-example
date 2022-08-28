package com.guilhermepalma.springsecurityexample.dto.exceptions;

public class NotFoundException extends Exception{

    public NotFoundException(String text, Object... attributes) {
        super(String.format(text, attributes));
    }
}
