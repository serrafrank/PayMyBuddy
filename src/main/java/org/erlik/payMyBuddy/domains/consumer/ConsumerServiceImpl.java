package org.erlik.payMyBuddy.domains.consumer;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.erlik.payMyBuddy.domains.ConsumerRepository;
import org.erlik.payMyBuddy.domains.consumer.events.AddFriendEvent;
import org.erlik.payMyBuddy.domains.consumer.events.CreateNewConsumerEvent;
import org.erlik.payMyBuddy.domains.consumer.events.FindConsumerByEmailEvent;
import org.erlik.payMyBuddy.domains.consumer.events.FindConsumerByIdEvent;
import org.erlik.payMyBuddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.payMyBuddy.domains.exceptions.EmailAlreadyExistsException;
import org.erlik.payMyBuddy.domains.models.Consumer;
import org.erlik.payMyBuddy.domains.models.EmailAddress;
import org.erlik.payMyBuddy.domains.models.Friend;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl
    implements ConsumerService {

    private final ConsumerRepository consumerRepository;

    @Override
    public UUID createConsumer(CreateNewConsumerEvent consumerEvent) {
        final var newConsumer = new Consumer(consumerEvent.firstname(),
            consumerEvent.lastname(),
            consumerEvent.emailAddress(),
            consumerEvent.password());

        if (consumerRepository.emailExists(newConsumer.emailAddress())) {
            throw new EmailAlreadyExistsException();
        }

        consumerRepository.createNewConsumer(newConsumer);
        return newConsumer.id();
    }

    @Override
    public Consumer findConsumerById(FindConsumerByIdEvent findConsumerByIdEvent) {
        final var consumerId = findConsumerByIdEvent.id();
        return getConsumerByIdOrThrowConsumerNotFoundException(consumerId);
    }

    @Override
    public Consumer findConsumerByEmail(FindConsumerByEmailEvent findConsumerByEmailEvent) {
        final var emailAddress = findConsumerByEmailEvent.email();
        return getConsumerByEmailOrThrowConsumerNotFoundException(emailAddress);
    }


    @Override
    public void addFriend(AddFriendEvent addFriendEvent) {
        final var friendEmailAddress = addFriendEvent.friendEmailAddress();
        final var friend = getFriendByEmailOrThrowConsumerNotFoundException(friendEmailAddress);

        final var consumerId = addFriendEvent.consumerId();
        final var consumer = getConsumerByIdOrThrowConsumerNotFoundException(consumerId)
            .addFriend(friend);

        consumerRepository.updateConsumer(consumer);
    }

    private Friend getFriendByEmailOrThrowConsumerNotFoundException(String emailAddress) {
        final var friendEmailAddress = new EmailAddress(emailAddress);
        return consumerRepository.getFriendByEmail(friendEmailAddress)
                                 .orElseThrow(() -> new ConsumerNotFoundException(friendEmailAddress));
    }

    private Consumer getConsumerByIdOrThrowConsumerNotFoundException(UUID consumer) {
        return consumerRepository.getConsumerById(consumer)
                                 .orElseThrow(() -> new ConsumerNotFoundException(consumer));
    }

    private Consumer getConsumerByEmailOrThrowConsumerNotFoundException(String emailAddress) {
        final var consumerEmailAddress = new EmailAddress(emailAddress);
        return consumerRepository.getConsumerByEmail(consumerEmailAddress)
                                 .orElseThrow(() -> new ConsumerNotFoundException(
                                     consumerEmailAddress));
    }


}
