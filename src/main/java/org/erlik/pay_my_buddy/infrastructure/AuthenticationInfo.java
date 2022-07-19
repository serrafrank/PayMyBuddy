package org.erlik.pay_my_buddy.infrastructure;

import java.util.Optional;
import org.erlik.pay_my_buddy.domains.models.Id;

public interface AuthenticationInfo {

    Optional<Id> getCurrentAuthenticatedUserId();
}
