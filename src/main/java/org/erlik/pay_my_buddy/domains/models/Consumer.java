package org.erlik.pay_my_buddy.domains.models;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.erlik.pay_my_buddy.domains.exceptions.FriendAlreadyExists;

public record Consumer(Id id,
                       String firstname,
                       String lastname,
                       EmailAddress emailAddress,
                       HashedPassword password,
                       Account account,
                       List<Friend> friends,
                       boolean isActive)
    implements ValueObject {

    public Consumer {
        if (id == null) {
            throw new IllegalArgumentException("Firstname is null, empty or blank");
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
        if (account == null) {
            throw new IllegalArgumentException("Account is null");
        }
        if (friends == null) {
            throw new IllegalArgumentException("Friends is null");
        }
    }

    public Consumer(String firstname,
                    String lastname,
                    String emailAddress,
                    HashedPassword password) {
        this(new Id(),
            firstname,
            lastname,
            new EmailAddress(emailAddress),
            password,
            new Account(),
            new ArrayList<>(),
            false);
    }

    /**
     * Return an activate Consumer with isActive set to true
     */
    public Consumer activate() {
        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            account,
            friends,
            true);
    }

    public Consumer inactivate() {
        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            account,
            friends,
            false);
    }

    public Consumer addFriend(Friend friend) {
        if (friends.stream().anyMatch(c -> c.id().equals(friend.id()))) {
            throw new FriendAlreadyExists(this, friend);
        }

        friends.add(friend);

        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            account,
            friends,
            isActive);
    }
}
