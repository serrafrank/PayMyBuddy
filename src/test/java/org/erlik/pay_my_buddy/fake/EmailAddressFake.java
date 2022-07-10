package org.erlik.pay_my_buddy.fake;

import org.erlik.pay_my_buddy.domains.models.EmailAddress;

public class EmailAddressFake {

    public static EmailAddress generateEmail() {
        return new EmailAddress(TestFaker.fake().internet().emailAddress());
    }

}
