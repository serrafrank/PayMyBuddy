package org.erlik.pay_my_buddy.core;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HashedPasswordEncoder {

    private HashedPasswordEncoder() {
    }

    public static String encode(String plainTextPassword) {
        return encoder().encode(plainTextPassword);
    }

    public static boolean matches(String plainTextPassword, String hashedPassword) {
        return encoder().matches(plainTextPassword, hashedPassword);
    }

    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
