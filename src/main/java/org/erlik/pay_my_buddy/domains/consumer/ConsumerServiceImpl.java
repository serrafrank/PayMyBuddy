package org.erlik.pay_my_buddy.domains.consumer;

import lombok.RequiredArgsConstructor;
import org.erlik.pay_my_buddy.domains.ConsumerRepository;
import org.erlik.pay_my_buddy.domains.consumer.events.AddFriendEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.CreateNewConsumerEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByEmailEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByIdEvent;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.pay_my_buddy.domains.exceptions.EmailAlreadyExistsException;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.HashedPassword;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl
    implements ConsumerService {

    private final ConsumerRepository consumerRepository;

    @Override
    public Id createConsumer(CreateNewConsumerEvent consumerEvent) {
        var hashedPassword = HashedPassword.fromPlainText(consumerEvent.password());
        final var newConsumer = new Consumer(consumerEvent.firstname(),
            consumerEvent.lastname(),
            consumerEvent.emailAddress(),
            hashedPassword);

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

    private Consumer getConsumerByIdOrThrowConsumerNotFoundException(Id consumer) {
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
