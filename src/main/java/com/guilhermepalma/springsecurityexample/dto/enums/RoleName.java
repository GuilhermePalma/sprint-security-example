package com.guilhermepalma.springsecurityexample.dto.enums;

public enum RoleName {
    ROLE_ADMIN("ADMIN"), ROLE_USER("USER");
    private final String text;

    RoleName(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return this.text;
    }
}
