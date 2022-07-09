package org.erlik.pay_my_buddy.domains;

import java.util.Optional;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;

public interface ConsumerRepository {

    boolean emailExists(EmailAddress emailAddress);

    void createNewConsumer(Consumer consumer);

    Optional<Consumer> getConsumerById(Id id);

    Optional<Consumer> getConsumerByEmail(EmailAddress emailAddress);

    Optional<Friend> getFriendByEmail(EmailAddress friendEmailAddress);

    void updateConsumer(Consumer consumer);
}
