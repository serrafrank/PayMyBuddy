package org.erlik.pay_my_buddy.core;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private PasswordEncoder() {
    }

    public static String encode(String plainTextPassword) {
        return encoder().encode(plainTextPassword);
    }

    public static boolean matches(String plainTextPassword, String hashedPassword) {
        return encoder().matches(plainTextPassword, hashedPassword);
    }

    private static BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
