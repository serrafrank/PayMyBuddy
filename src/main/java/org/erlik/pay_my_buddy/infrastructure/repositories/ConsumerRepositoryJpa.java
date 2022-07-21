package org.erlik.pay_my_buddy.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;
import org.erlik.pay_my_buddy.infrastructure.entities.ConsumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepositoryJpa
    extends JpaRepository<ConsumerEntity, UUID> {

    boolean existsByEmailAddress(String emailAddress);

    Optional<ConsumerEntity> findByEmailAddress(String emailAddress);
}
