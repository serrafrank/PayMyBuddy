package org.erlik.pay_my_buddy.fake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;

public class FriendFake {

    public static FriendFakeBuilder builder() {
        return new FriendFakeBuilder();
    }

    public static Friend generateFriend() {
        return builder().build();
    }

    @With
    @Getter
    @AllArgsConstructor
    static class FriendFakeBuilder {

        private Id id;
        private String firstname;
        private String lastname;
        private EmailAddress emailAddress;

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
