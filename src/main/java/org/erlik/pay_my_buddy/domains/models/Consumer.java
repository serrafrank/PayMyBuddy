package org.erlik.pay_my_buddy.domains.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.erlik.pay_my_buddy.core.validator.Validator;
import org.erlik.pay_my_buddy.domains.exceptions.AccountTypeAlreadyExists;
import org.erlik.pay_my_buddy.domains.exceptions.FriendAlreadyExistsException;
import org.erlik.pay_my_buddy.domains.models.accounts.AccountType;
import org.erlik.pay_my_buddy.domains.models.accounts.ElectronicMoneyAccount;

public record Consumer(Id id,
                       String firstname,
                       String lastname,
                       EmailAddress emailAddress,
                       Password password,
                       Set<Account> accounts,
                       Set<Friend> friends,
                       boolean isActive)
    implements ValueObject {

    public Consumer {
        Validator.of(id)
                 .isNull()
                 .thenThrow("Id is null");

        Validator.of(firstname)
                 .isBlank()
                 .thenThrow("Firstname is null, empty or blank");

        Validator.of(lastname)
                 .isBlank()
                 .thenThrow("lastname is null, empty or blank");

        Validator.of(emailAddress)
                 .isNull()
                 .thenThrow("emailAddress is null");

        Validator.of(password)
                 .isNull()
                 .thenThrow("Password is null");

        Validator.of(accounts)
                 .isNull()
                 .thenThrow("accounts is null");

        Validator.of(friends)
                 .isNull()
                 .thenThrow("friends is null");

        accounts = Collections.unmodifiableSet(accounts);
        friends = Collections.unmodifiableSet(friends);
    }

    public Consumer(String firstname,
                    String lastname,
                    String emailAddress,
                    Password password) {
        this(new Id(),
            firstname,
            lastname,
            new EmailAddress(emailAddress),
            password,
            initAccountList(),
            initFriendList(),
            false);
    }

    private static Set<Account> initAccountList() {
        return Set.of(new ElectronicMoneyAccount());
    }

    private static Set<Friend> initFriendList() {
        return Set.of();
    }

    /**
     * @return Return an activated Consumer with isActive set to true
     */
    public Consumer activate() {
        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            accounts,
            friends,
            true);
    }

    /**
     * @return Return an inactivated Consumer with isActive set to false
     */
    public Consumer inactivate() {
        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            accounts,
            friends,
            false);
    }

    public Consumer addFriend(Friend friend) {
        if (friends.stream().anyMatch(c -> c.id().equals(friend.id()))) {
            throw new FriendAlreadyExistsException(this, friend);
        }

        Set<Friend> mutableFriendList = new HashSet<>(friends);

        mutableFriendList.add(friend);

        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            accounts,
            Collections.unmodifiableSet(mutableFriendList),
            isActive);
    }

    public Consumer addAccount(Account account) {
        if (accounts.stream().anyMatch(c -> c.accountType().equals(account.accountType()))) {
            throw new AccountTypeAlreadyExists(this, account);
        }

        final Set<Account> mutableAccountList = new HashSet<>(accounts);

        mutableAccountList.add(account);

        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            Collections.unmodifiableSet(mutableAccountList),
            friends,
            isActive);
    }

    public Optional<Account> getAccountByType(AccountType accountType) {
        return accounts.stream()
                       .filter(account -> account.accountType().equals(accountType))
                       .findFirst();
    }
}
