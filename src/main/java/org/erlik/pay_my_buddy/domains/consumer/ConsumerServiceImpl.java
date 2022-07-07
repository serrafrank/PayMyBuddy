package org.erlik.pay_my_buddy.domains.consumer;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.erlik.pay_my_buddy.domains.ConsumerRepository;
import org.erlik.pay_my_buddy.domains.consumer.events.CreateNewConsumerEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByEmailEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByIdEvent;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.pay_my_buddy.domains.exceptions.EmailAlreadyExistsException;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
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
        final var id = findConsumerByIdEvent.id();
        return consumerRepository.getConsumerById(id)
            .orElseThrow(() -> new ConsumerNotFoundException(id));
    }

    @Override
    public Consumer findConsumerByEmail(FindConsumerByEmailEvent findConsumerByEmailEvent) {
        final var email = new EmailAddress(findConsumerByEmailEvent.email());
        return consumerRepository.getConsumerByEmail(email)
            .orElseThrow(() -> new ConsumerNotFoundException(email));
    }
}
