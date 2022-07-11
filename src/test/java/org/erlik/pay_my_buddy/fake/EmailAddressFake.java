package org.erlik.pay_my_buddy.fake;

import lombok.AllArgsConstructor;
import lombok.With;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;

public class EmailAddressFake {

    public static EmailAddressBuilder builder() {
        return new EmailAddressBuilder(TestFaker.fake().internet().emailAddress());
    }

    public static EmailAddress generateEmail() {
        return builder().build();
    }

    @With
    @AllArgsConstructor
    static class EmailAddressBuilder {

        private String email;

        public EmailAddress build() {
            return new EmailAddress(email);
        }
    }

}
