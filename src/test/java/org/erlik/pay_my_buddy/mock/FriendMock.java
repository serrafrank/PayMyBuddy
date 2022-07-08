package org.erlik.pay_my_buddy.mock;

import java.util.UUID;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;

public class FriendMock {

    public static Friend create() {
        return new Friend(
            UUID.randomUUID(),
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            new EmailAddress(TestFaker.fake().internet().emailAddress()));
    }
}
