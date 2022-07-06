package org.erlik.payMyBuddy.domains.models;

import org.erlik.payMyBuddy.domains.exceptions.ExcludeDomainNameException;
import org.erlik.payMyBuddy.domains.exceptions.InvalidEmailAddressException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class EmailAddressUnitTest {

    private static Object[][] validEmailsProviderTest() {
        return new Object[][]{
            {"username@domain.com"},
            {"username@domain.co.in"},
            {"user.name@domain.com"},
            {"user_name@domain.com"},
            {"username@domain.corporate.in"},
            };
    }

    private static Object[][] invalidEmailProviderTest() {
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
    @DisplayName("when I use an valid email then it not throws an exception")
    public void initWithValidEmail(String email) {
        Assertions.assertDoesNotThrow(() -> new EmailAddress(email));
    }

    @ParameterizedTest
    @MethodSource("invalidEmailProvider")
    @DisplayName("when I use an invalid email then it throws an InvalidEmailAddressException")
    public void initWithInvalidEmailThrowInvalidEmailAddressException(String email) {
        Assertions.assertThrows(InvalidEmailAddressException.class,
            () -> new EmailAddress(email));
    }

    @Test
    @DisplayName("when I use an exclude domainName then it throws an ExcludeDomainNameException")
    public void initWithExcludDomainNameThrowExcludeDomainNameExceptionTest() {
        Assertions.assertThrows(ExcludeDomainNameException.class,
            () -> new EmailAddress("email@domain.org", "domain.org"));
    }
}
