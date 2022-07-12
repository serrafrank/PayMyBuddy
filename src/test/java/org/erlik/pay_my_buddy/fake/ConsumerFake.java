package org.erlik.pay_my_buddy.fake;

import java.util.Set;
import lombok.Builder;
import org.erlik.pay_my_buddy.domains.models.Account;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Password;
import org.erlik.pay_my_buddy.domains.models.accounts.ElectronicMoneyAccount;

@Builder
public class ConsumerFake {

    private Id id;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private String password;
    private Set<Account> accounts;
    private Set<Friend> friends;
    private boolean isActive;


    public static Consumer generateInactiveConsumer() {
        return builder().isActive(false).build();
    }

    public static Consumer generateActiveConsumer() {
        return builder().isActive(true).build();
    }

    public static class ConsumerFakeBuilder {

        private ConsumerFakeBuilder() {
            id = new Id();
            firstname = TestFaker.fake().name().firstName();
            lastname = TestFaker.fake().name().lastName();
            emailAddress = TestFaker.fake().internet().emailAddress();
            password = TestFaker.generateValidPlainTextPassword();
            accounts = defaultAccounts();
            friends = defaultFriends();
            isActive = true;
        }

        public Consumer build() {
            return new Consumer(id,
                firstname,
                lastname,
                new EmailAddress(emailAddress),
                new Password(password),
                accounts,
                friends,
                isActive);
        }

        private Set<Friend> defaultFriends() {
            return Set.of();
        }


        private Set<Account> defaultAccounts() {
            return Set.of(new ElectronicMoneyAccount());
        }
    }
}
