package org.erlik.pay_my_buddy.fake;

import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;

public class FriendFake {

    public static Friend generateFriend() {
        return new Friend(
            new Id(),
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            new EmailAddress(TestFaker.fake().internet().emailAddress()));
    }
}
