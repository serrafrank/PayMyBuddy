package org.erlik.pay_my_buddy.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.repositories.ConsumerRepository;
import org.erlik.pay_my_buddy.infrastructure.entities.ConsumerEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerRepositoryImpl
    implements ConsumerRepository {

    private final ConsumerRepositoryJpa repository;

    @Override
    public boolean emailExists(EmailAddress emailAddress) {
        return repository.existsByEmailAddress(emailAddress.email());
    }

    @Override
    public void createNewConsumer(Consumer consumer) {
        final var consumerEntity = new ConsumerEntity(consumer);
        repository.save(consumerEntity);
    }

    @Override
    public Optional<Consumer> getConsumerById(Id id) {
        return repository.findById(id.id())
                         .map(ConsumerEntity::toConsumer);
    }

    @Override
    public Optional<Consumer> getConsumerByEmail(EmailAddress emailAddress) {
        return repository.findByEmailAddress(emailAddress.email())
                         .map(ConsumerEntity::toConsumer);
    }

    @Override
    public Optional<Friend> getFriendByEmail(EmailAddress friendEmailAddress) {
        return repository.findByEmailAddress(friendEmailAddress.email())
                         .map(ConsumerEntity::toFriend);
    }

    @Override
    public Optional<Friend> getFriendById(Id friendEmailId) {
        return repository.findById(friendEmailId.id())
                         .map(ConsumerEntity::toFriend);
    }

    @Override
    public void updateConsumer(Consumer consumer) {
        final var consumerEntity = new ConsumerEntity(consumer);
        repository.save(consumerEntity);

    }

    @Override
    public List<Friend> getAllFriendsByConsumerId(Id consumerId) {
        return repository.findById(consumerId.id())
                         .map(ConsumerEntity::toConsumer)
                         .map(Consumer::friends)
                         .orElse(List.of());
    }
}
