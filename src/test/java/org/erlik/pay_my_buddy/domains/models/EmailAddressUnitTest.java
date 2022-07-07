package org.erlik.pay_my_buddy.domains.models;

import org.erlik.pay_my_buddy.domains.exceptions.InvalidEmailAddressException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class EmailAddressUnitTest {

    private static Object[][] validEmailsProvider() {
        return new Object[][]{
            {"username@domain.com"},
            {"username@domain.co.in"},
            {"user.name@domain.com"},
            {"user_name@domain.com"},
            {"username@domain.corporate.in"},
            };
    }

    private static Object[][] invalidEmailProvider() {
        return new Object[][]{
            {"username@domain"},
            {".username@domain.com"},
            {"username@domain.com."},
            {"username@domain..com"},
            {"username@domain.c"},
            {"username@domain.corporate"},
            };
        }

        @ParameterizedTest
        @MethodSource("validEmailsProvider")
        @DisplayName("when I use a valid email then it not throws an exception")
        void initWithValidEmail (String email){
            Assertions.assertDoesNotThrow(() -> new EmailAddress(email));
        }

        @ParameterizedTest
        @MethodSource("invalidEmailProvider")
        @DisplayName("when I use an invalid email then it throws an InvalidEmailAddressException")
        void initWithInvalidEmailThrowInvalidEmailAddressException (String email){
            Assertions.assertThrows(InvalidEmailAddressException.class,
                () -> new EmailAddress(email));
        }
    }
