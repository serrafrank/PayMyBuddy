package org.erlik.pay_my_buddy.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.erlik.pay_my_buddy.domains.ConsumerRepository;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;

public class ConsumerRepositoryMock implements ConsumerRepository {

    private final List<Consumer> consumers = new ArrayList<>();

    @Override
    public boolean emailExists(EmailAddress emailAddress) {
        return consumers.stream().anyMatch(consumer -> consumer.emailAddress().equals(emailAddress));
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
        return consumers.stream().filter(consumer -> consumer.emailAddress().equals(emailAddress)).findFirst();
    }

    @Override
    public Optional<Friend> getFriendByEmail(EmailAddress friendEmailAddress) {
        return Optional.empty();
    }

    @Override
    public void updateConsumer(Consumer consumer) {
        consumers.removeIf(c -> c.id().equals(consumer.id()));
        consumers.add(consumer);
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }
}
