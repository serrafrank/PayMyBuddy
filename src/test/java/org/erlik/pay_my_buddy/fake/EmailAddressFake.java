package org.erlik.pay_my_buddy.fake;

import lombok.Builder;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;

@Builder
public class EmailAddressFake {

    private String email;

    public static EmailAddress generateEmail() {
        return builder().build();
    }

    public static class EmailAddressFakeBuilder {

        private EmailAddressFakeBuilder() {
            email = TestFaker.fake().internet().emailAddress();
        }

        public EmailAddress build() {
            return new EmailAddress(email);
        }
    }

}
