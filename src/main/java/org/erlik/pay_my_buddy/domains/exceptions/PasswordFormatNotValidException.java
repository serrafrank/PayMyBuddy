package org.erlik.pay_my_buddy.domains.exceptions;

import java.util.List;

public class PasswordFormatNotValidException
    extends RuntimeException {

    public PasswordFormatNotValidException(String plainTextPassword,
                                           List<String> validationErrors) {
        super("Password is invalid : " + plainTextPassword + validationErrors.toString());
    }
}
