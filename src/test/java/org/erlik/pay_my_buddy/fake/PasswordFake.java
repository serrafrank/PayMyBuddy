package org.erlik.pay_my_buddy.fake;

import lombok.Builder;
import org.erlik.pay_my_buddy.domains.models.Password;

@Builder
public class PasswordFake {

    private String plainTextPassword;

    public static Password generateHashedPassword() {
        return builder().build();
    }

    public static class PasswordFakeBuilder {

        private PasswordFakeBuilder() {
            plainTextPassword = TestFaker.generateValidPlainTextPassword();
        }

        public Password build() {
            return new Password(plainTextPassword);
        }
    }
}
