package org.erlik.payMyBuddy.mock;

import java.util.UUID;
import org.erlik.payMyBuddy.domains.models.EmailAddress;
import org.erlik.payMyBuddy.domains.models.Friend;

public class FriendMock {

    public static Friend create() {
        return new Friend(
            UUID.randomUUID(),
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            new EmailAddress(TestFaker.fake().internet().emailAddress()));
    }
}
