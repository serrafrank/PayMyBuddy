package org.erlik.pay_my_buddy.infrastructure.repositories;

import java.util.List;
import java.util.UUID;
import org.erlik.pay_my_buddy.infrastructure.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepositoryJpa
    extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findAllByDebtorIdOrCreditorIdOrderByCreationDate(UUID debtorId,
                                                                             UUID creditorId);

}
