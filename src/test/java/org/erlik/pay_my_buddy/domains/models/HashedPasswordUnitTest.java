package org.erlik.pay_my_buddy.domains.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.erlik.pay_my_buddy.domains.exceptions.PasswordFormatNotValidException;
import org.erlik.pay_my_buddy.fake.HashedPasswordFake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class HashedPasswordUnitTest {

    private static Object[][] blankProvider() {
        return new Object[][]{
            {null},
            {""},
            {" "},
            {"     "}
        };
    }

    private static Object[][] invalidPasswordProvider() {
        return new Object[][]{
            {"aA0"},
            {"aa0!"},
            {"aAA!"},
            {"aA00"}
        };
    }

    @Test
    @DisplayName("given a plain text password when the password is hashed then the hashed password match with plain text password")
    void initWithPasswordFromPlainText() {
        //GIVEN
        String password = HashedPasswordFake.generateValidPlainTextPassword();

        //WHEN
        HashedPassword response = HashedPassword.fromPlainText(password);

        //THEN
        assertThat(response).isNotNull();
        assertThat(response.hashedPassword()).isNotNull();
        assertThat(response.matchWith(password)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("blankProvider")
    @DisplayName("given a plain text password when the password is hashed then the hashed password match with plain text password")
    void initWithNullOrBlankTextThrowIllegalArgumentExceptionException(String invalidPassword) {

        //WHEN
        Executable executable = () -> HashedPassword.fromPlainText(invalidPassword);

        //THEN
        assertThrows(IllegalArgumentException.class, executable);
    }

    @ParameterizedTest
    @MethodSource("invalidPasswordProvider")
    @DisplayName("given a plain text password when the password is hashed then the hashed password match with plain text password")
    void initWithInvalidTextThrowIllegalArgumentExceptionException(String invalidPassword) {

        //WHEN
        Executable executable = () -> HashedPassword.fromPlainText(invalidPassword);

        //THEN
        assertThrows(PasswordFormatNotValidException.class, executable);
    }

    @ParameterizedTest
    @MethodSource("blankProvider")
    @DisplayName("given a hashed password when the password is hashed then the hashed password match with plain text password")
    void initWithInvalidHashedPasswordThrowIllegalArgumentExceptionException(String invalidPassword) {

        //WHEN
        Executable executable = () -> new HashedPassword(invalidPassword);

        //THEN
        assertThrows(IllegalArgumentException.class, executable);
    }

}
