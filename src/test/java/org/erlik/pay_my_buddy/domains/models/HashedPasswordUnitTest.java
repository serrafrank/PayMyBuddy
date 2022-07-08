package org.erlik.pay_my_buddy.domains.models;

import org.erlik.pay_my_buddy.domains.exceptions.PasswordFormatNotValidException;
import org.erlik.pay_my_buddy.mock.HashedPasswordMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.provider.MethodSource;

class HashedPasswordUnitTest {

    private static Object[][] invalidPlainTextPasswordProvider() {
        return new Object[][]{
            {null},
            {""},
            {" "},
            {"     "},
            {"     "},
        };
    }

    @Test
    @DisplayName("given a plain text password when the password is hashed then the hashed password match with plain text password")
    void initWithPasswordFromPlainText() {
        //GIVEN
        String password = HashedPasswordMock.generateValidPlainTextPassword();

        //WHEN
        HashedPassword response = HashedPassword.fromPlainText(password);

        //THEN
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.hashedPassword());
        Assertions.assertTrue(response.matchWith(password));
    }

    @MethodSource("invalidPlainTextPasswordProvider")
    @DisplayName("given a plain text password when the password is hashed then the hashed password match with plain text password")
    void initWithInvalidTextThrowPasswordFormatNotValidException(String invalidPassword) {

        //WHEN
        Executable executable = () -> HashedPassword.fromPlainText(invalidPassword);

        //THEN
        Assertions.assertThrows(PasswordFormatNotValidException.class, executable);
    }

}
