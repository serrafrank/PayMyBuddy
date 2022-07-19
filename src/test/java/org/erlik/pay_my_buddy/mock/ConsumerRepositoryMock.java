package org.erlik.pay_my_buddy.mock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.repositories.ConsumerRepository;

public class ConsumerRepositoryMock
    implements ConsumerRepository {

    private final List<Consumer> consumers = new ArrayList<>();

    @Override
    public boolean emailExists(EmailAddress emailAddress) {
        return consumers.stream()
            .anyMatch(consumer -> consumer.emailAddress().equals(emailAddress));
    }

    @Override
    public void createNewConsumer(Consumer consumer) {
        consumers.add(consumer);
    }

    @Override
    public Optional<Consumer> getConsumerById(Id id) {
        return consumers.stream().filter(consumer -> consumer.id().equals(id)).findFirst();
    }

    @Override
    public Optional<Consumer> getConsumerByEmail(EmailAddress emailAddress) {
        return consumers.stream()
            .filter(consumer -> consumer.emailAddress().equals(emailAddress))
            .findFirst();
    }

    @Override
    public Optional<Friend> getFriendByEmail(EmailAddress friendEmailAddress) {
        return consumers
            .stream()
            .filter(consumer -> consumer.emailAddress().equals(friendEmailAddress))
            .map(Friend::new)
            .findFirst();
    }

    @Override
    public void updateConsumer(Consumer consumer) {
        consumers.removeIf(c -> c.id().equals(consumer.id()));
        consumers.add(consumer);
    }

    @Override
    public List<Friend> getAllFriendsByConsumerId(Id consumerId) {
        return getConsumerById(consumerId)
            .map(value -> value.friends()
                               .stream()
                               .sorted(Comparator.comparing(Friend::lastname))
                               .collect(Collectors.toList())).orElseGet(List::of);
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void addFriendToConsumerFriendList(Id id, Friend friend) {
        consumers.stream().filter(c -> c.id().equals(id))
                 .forEach(c -> consumers.set(consumers.indexOf(c), c.addFriend(friend)));
    }
}
