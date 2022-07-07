package org.erlik.payMyBuddy.domains;

import java.util.Optional;
import java.util.UUID;
import org.erlik.payMyBuddy.domains.models.Consumer;
import org.erlik.payMyBuddy.domains.models.EmailAddress;
import org.erlik.payMyBuddy.domains.models.Friend;

public interface ConsumerRepository {

    boolean emailExists(EmailAddress emailAddress);

    void createNewConsumer(Consumer consumer);

    Optional<Consumer> getConsumerById(UUID id);

    Optional<Consumer> getConsumerByEmail(EmailAddress emailAddress);

    Optional<Friend> getFriendByEmail(EmailAddress friendEmailAddress);

    void updateConsumer(Consumer consumer);
}
