package org.erlik.pay_my_buddy.mock;

import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;

public class FriendMock {

    public static Friend create() {
        return new Friend(
            new Id(),
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            new EmailAddress(TestFaker.fake().internet().emailAddress()));
    }
}
