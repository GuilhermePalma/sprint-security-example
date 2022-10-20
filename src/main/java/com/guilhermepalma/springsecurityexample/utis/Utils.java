package com.guilhermepalma.springsecurityexample.utis;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class Utils {

    /**
     * Return the class {@link PasswordEncoder} to encode Password in Database
     */
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Check if item is null
     */
    public static <T> boolean isNull(T value) {
        return value == null;
    }

    /**
     * Check if especify {@link List} is null or empty
     *
     * @see #isNull(Object)
     */
    public static boolean isNullOrEmpty(Collection<?> list) {
        return isNull(list) || list.isEmpty();
    }

    /**
     * Check if especify {@link String} is null or empty
     *
     * @see #isNull(Object)
     */
    public static boolean isNullOrEmpty(String value) {
        return isNull(value) || value.isEmpty();
    }

}
