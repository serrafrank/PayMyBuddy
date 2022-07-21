package org.erlik.pay_my_buddy.domains.exceptions;

import java.util.List;
import org.erlik.pay_my_buddy.core.exceptions.BadRequestException;

public class PasswordFormatNotValidException
    extends BadRequestException {

    public PasswordFormatNotValidException(String plainTextPassword,
                                           List<String> validationErrors) {
        super("Password is invalid : " + plainTextPassword + validationErrors.toString());
    }
}
