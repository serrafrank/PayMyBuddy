package org.erlik.pay_my_buddy.domains.consumer.queries;

import lombok.RequiredArgsConstructor;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.repositories.ConsumerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerQueryServiceImpl
    implements ConsumerQueryService {

    private final ConsumerRepository consumerRepository;

    @Override
    public Consumer findConsumerById(FindConsumerByIdQuery findConsumerByIdQuery) {
        final var consumerId = findConsumerByIdQuery.id();
        return consumerRepository.getConsumerById(consumerId)
            .orElseThrow(() -> new ConsumerNotFoundException(consumerId));
    }

    @Override
    public Consumer findConsumerByEmail(FindConsumerByEmailQuery findConsumerByEmailQuery) {
        final var consumerEmailAddress = new EmailAddress(findConsumerByEmailQuery.email());
        return consumerRepository.getConsumerByEmail(consumerEmailAddress)
            .orElseThrow(() -> new ConsumerNotFoundException(
                consumerEmailAddress));
    }
}
