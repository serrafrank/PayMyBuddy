package org.erlik.pay_my_buddy.mock;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.erlik.pay_my_buddy.domains.models.Account;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.HashedPassword;
import org.erlik.pay_my_buddy.domains.models.Id;

@Getter
public class ConsumerMock {

    public static ConsumerMockBuilder builder() {
        return new ConsumerMockBuilder(
            new Id(),
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            TestFaker.fake().internet().emailAddress(),
            HashedPasswordMock.generateValidPlainTextPassword(),
            new HashSet<>(),
            new HashSet<>(),
            false
        );
    }

    public static Consumer create() {
        return builder().build();
    }

    public static Consumer inactive() {
        return builder().withActive(false).build();
    }

    public static Consumer active() {
        return builder().withActive(true).build();
    }

    @With
    @AllArgsConstructor
    public static class ConsumerMockBuilder {

        private Id id;
        private String firstname;
        private String lastname;
        private String emailAddress;
        private String plainTextPassword;
        private Set<Account> accounts;
        private Set<Friend> friends;
        private boolean isActive;

        public Consumer build() {
            return new Consumer(
                id,
                firstname,
                lastname,
                new EmailAddress(emailAddress),
                HashedPassword.fromPlainText(plainTextPassword),
                accounts,
                friends,
                isActive
            );
        }
    }
}
