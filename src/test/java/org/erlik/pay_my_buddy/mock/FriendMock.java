package org.erlik.pay_my_buddy.mock;

import lombok.AllArgsConstructor;
import lombok.With;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;

public class FriendMock {

    public static FriendMockBuilder builder() {
        return new FriendMockBuilder(
            new Id(),
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            TestFaker.fake().internet().emailAddress()
        );
    }

    public static Friend create() {
        return builder().build();
    }

    @With
    @AllArgsConstructor
    public static class FriendMockBuilder {

        private Id id;
        private String firstname;
        private String lastname;
        private String emailAddress;

        public Friend build() {
            return new Friend(
                id,
                firstname,
                lastname,
                new EmailAddress(emailAddress)
            );
        }
    }
}
