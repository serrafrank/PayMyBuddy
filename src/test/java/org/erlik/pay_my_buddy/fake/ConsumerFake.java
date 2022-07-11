package org.erlik.pay_my_buddy.fake;

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
import org.erlik.pay_my_buddy.domains.models.accounts.ElectronicMoneyAccount;

public class ConsumerFake {

    public static ConsumerFakeBuilder builder() {
        return new ConsumerFakeBuilder();
    }

    public static Consumer generateConsumer() {
        return builder().build();
    }

    public static Consumer inactive() {
        return builder().withActive(false).build();
    }

    public static Consumer active() {
        return builder().withActive(true).build();
    }

    @With
    @Getter
    @AllArgsConstructor
    static class ConsumerFakeBuilder {

        private Id id;
        private String firstname;
        private String lastname;
        private String emailAddress;
        private String password;
        private Set<Account> accounts;
        private Set<Friend> friends;
        private boolean isActive;

        private ConsumerFakeBuilder() {
            id = new Id();
            firstname = TestFaker.fake().name().firstName();
            lastname = TestFaker.fake().name().lastName();
            emailAddress = TestFaker.fake().internet().emailAddress();
            password = TestFaker.generateValidPlainTextPassword();
            accounts =  Set.of(new ElectronicMoneyAccount());
            friends = Set.of();
            isActive = true;
        }

        public Consumer build() {
            return new Consumer(id,
                firstname,
                lastname,
                new EmailAddress(emailAddress),
                HashedPassword.fromPlainText(password),
                accounts,
                friends,
                isActive);
        }
    }
}
