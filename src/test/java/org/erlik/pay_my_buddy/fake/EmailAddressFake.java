package org.erlik.pay_my_buddy.fake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;

public class EmailAddressFake {

    public static EmailAddressFakeBuilder builder() {
        return new EmailAddressFakeBuilder();
    }

    public static EmailAddress generateEmail() {
        return builder().build();
    }

    @With
    @Getter
    @AllArgsConstructor
    static class EmailAddressFakeBuilder {

        private String email;

        private EmailAddressFakeBuilder() {
            email = TestFaker.fake().internet().emailAddress();
        }

        public EmailAddress build() {
            return new EmailAddress(email);
        }
    }

}
