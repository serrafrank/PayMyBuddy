package org.erlik.pay_my_buddy.domains.consumer.commands;

import lombok.RequiredArgsConstructor;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.pay_my_buddy.domains.exceptions.EmailAlreadyExistsException;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Password;
import org.erlik.pay_my_buddy.domains.repositories.ConsumerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerCommandServiceImpl
    implements ConsumerCommandService {

    private final ConsumerRepository consumerRepository;

    @Override
    public Id createConsumer(CreateNewConsumerCommand consumerEvent) {
        var hashedPassword = new Password(consumerEvent.password());
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
    public void addFriend(AddFriendCommand addFriendCommand) {
        final var friend = consumerRepository.getFriendById(addFriendCommand.friendId())
            .orElseThrow(() -> new ConsumerNotFoundException(
                addFriendCommand.friendId()));

        final var consumerId = addFriendCommand.consumerId();
        final var consumer = consumerRepository.getConsumerById(consumerId)
            .orElseThrow(() -> new ConsumerNotFoundException(
                consumerId))
            .addFriend(friend);

        consumerRepository.updateConsumer(consumer);
    }
}
