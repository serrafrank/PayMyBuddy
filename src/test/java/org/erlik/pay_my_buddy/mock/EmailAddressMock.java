package org.erlik.pay_my_buddy.mock;

import org.erlik.pay_my_buddy.domains.models.EmailAddress;

public class EmailAddressMock {

    public static EmailAddress create() {
        return new EmailAddress(TestFaker.fake().internet().emailAddress());
    }

}
