package org.erlik.pay_my_buddy.mock;

import java.util.Optional;
import org.erlik.pay_my_buddy.application.AuthenticationInfo;
import org.erlik.pay_my_buddy.domains.models.Id;

public class AuthenticationInfoMock
    implements AuthenticationInfo {

    private Optional<Id> id;

    public AuthenticationInfoMock() {
        this.id = Optional.empty();
    }

    public void isAuthenticated(Id id) {
        this.id = Optional.of(id);
    }

    @Override
    public Optional<Id> getCurrentAuthenticatedUserId() {
        return id;
    }

}
