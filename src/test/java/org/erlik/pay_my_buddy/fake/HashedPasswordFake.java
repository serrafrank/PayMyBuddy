package org.erlik.pay_my_buddy.fake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.erlik.pay_my_buddy.domains.models.HashedPassword;

public class HashedPasswordFake {

    public static HashedPasswordFakeBuilder builder() {
        return new HashedPasswordFakeBuilder();
    }

    public static HashedPassword generateHashedPassword() {
        return builder().build();
    }

    @With
    @AllArgsConstructor
    @Getter
    static class HashedPasswordFakeBuilder {

        private String plainTextPassword;

        private HashedPasswordFakeBuilder() {
            plainTextPassword = TestFaker.generateValidPlainTextPassword() ;
        }

        public HashedPassword build() {
            return HashedPassword.fromPlainText(plainTextPassword);
        }

    }


}
