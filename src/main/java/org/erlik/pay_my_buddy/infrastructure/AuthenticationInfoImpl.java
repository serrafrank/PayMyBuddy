package org.erlik.pay_my_buddy.infrastructure;

import java.util.Optional;
import org.erlik.pay_my_buddy.application.AuthenticationInfo;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationInfoImpl
    implements AuthenticationInfo {

    @Override
    public Optional<Id> getCurrentAuthenticatedUserId() {
        return Optional.of(((Consumer) getAuthentication().getPrincipal()).id());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
