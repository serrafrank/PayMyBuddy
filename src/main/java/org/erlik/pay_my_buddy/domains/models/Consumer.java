package org.erlik.pay_my_buddy.domains.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                       List<Account> accounts,
                       List<Friend> friends,
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

        accounts = unmodifiableList(accounts);
        friends = unmodifiableList(friends);
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

    private static List<Account> initAccountList() {
        return List.of(new ElectronicMoneyAccount());
    }

    private static List<Friend> initFriendList() {
        return List.of();
    }

    /**
     * @return Return an activated Consumer with isActive list to true
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
     * @return Return an inactivated Consumer with isActive list to false
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

        var addToUnmodifiableFriendList = addToUnmodifiableList(friends, friend);

        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            accounts,
            addToUnmodifiableFriendList,
            isActive);
    }

    public Consumer addAccount(Account account) {
        if (accounts.stream().anyMatch(c -> c.accountType().equals(account.accountType()))) {
            throw new AccountTypeAlreadyExists(this, account);
        }

        var addToUnmodifiableAccountList = addToUnmodifiableList(accounts, account);

        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            addToUnmodifiableAccountList,
            friends,
            isActive);
    }

    public Optional<Account> getAccountByType(AccountType accountType) {
        return accounts.stream()
            .filter(account -> account.accountType().equals(accountType))
            .findFirst();
    }

    private <P> List<P> addToUnmodifiableList(List<P> list, P element) {
        var modifiableList = new ArrayList<>(list);
        modifiableList.add(element);
        return unmodifiableList(modifiableList);
    }

    private <P> List<P> unmodifiableList(List<P> list) {
        return List.copyOf(list);
    }
}
