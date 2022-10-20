package com.guilhermepalma.springsecurityexample.dto.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleName {
    ROLE_ADMIN("ADMIN"), ROLE_USER("USER");

    @JsonValue
    private final String text;

    RoleName(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return this.text;
    }
}
