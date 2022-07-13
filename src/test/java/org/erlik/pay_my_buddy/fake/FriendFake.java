package org.erlik.pay_my_buddy.fake;

import lombok.Builder;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;

@Builder
public class FriendFake {

    private Id id;
    private String firstname;
    private String lastname;
    private EmailAddress emailAddress;

    public static Friend generateFriend() {
        return builder().build();
    }

    public static class FriendFakeBuilder {

        private FriendFakeBuilder() {
            id = new Id();
            firstname = TestFaker.fake().name().firstName();
            lastname = TestFaker.fake().name().lastName();
            emailAddress = EmailAddressFake.generateEmail();
        }

        public Friend build() {
            return new Friend(id,
                firstname,
                lastname,
                emailAddress);
        }

    }
}
