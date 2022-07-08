package org.erlik.payMyBuddy.domains.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.erlik.payMyBuddy.domains.exceptions.FriendAlreadyExists;

public record Consumer(UUID id,
                       String firstname,
                       String lastname,
                       EmailAddress emailAddress,
                       HashedPassword password,
                       Account account,
                       List<Friend> friends,
                       boolean isActive)
    implements ValueObject {

    public Consumer(String firstname,
                    String lastname,
                    String emailAddress,
                    HashedPassword password) {
        this(UUID.randomUUID(), firstname, lastname, new EmailAddress(emailAddress), password,
            new Account(), new ArrayList<>(), false);
    }

    public Consumer active() {
        return new Consumer(id,
            firstname,
            lastname,
            emailAddress,
            password,
            account,
            friends,
            true);
    }

    public Consumer inactive() {
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
