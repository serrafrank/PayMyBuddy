package org.erlik.pay_my_buddy.domains.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
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
        if (id == null) {
            throw new IllegalArgumentException("Id is null, empty or blank");
        }
        if (StringUtils.isBlank(firstname)) {
            throw new IllegalArgumentException("Firstname is null, empty or blank");
        }
        if (StringUtils.isBlank(lastname)) {
            throw new IllegalArgumentException("Lastname is null, empty or blank");
        }
        if (emailAddress == null) {
            throw new IllegalArgumentException("EmailAddress is null");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password is null");
        }
        if (accounts == null) {
            throw new IllegalArgumentException("Account is null");
        }
        if (friends == null) {
            throw new IllegalArgumentException("Friends is null");
        }

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
        return accounts.stream().filter(account -> account.accountType().equals(accountType))
                       .findFirst();
    }
}
