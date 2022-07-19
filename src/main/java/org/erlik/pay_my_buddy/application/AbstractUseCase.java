package org.erlik.pay_my_buddy.application;

import org.erlik.pay_my_buddy.domains.models.Id;
import org.springframework.security.authentication.BadCredentialsException;

public abstract class AbstractUseCase {

    private final AuthenticationInfo authenticationInfo;

    protected AbstractUseCase(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    protected void throwExceptionIfNotAuthenticated() {
        if (authenticationInfo.getCurrentAuthenticatedUserId().isEmpty()) {
            throw new BadCredentialsException("Consumer not authenticated");
        }
    }

    protected void throwExceptionIfAuthenticated() {
        if (authenticationInfo.getCurrentAuthenticatedUserId().isPresent()) {
            throw new BadCredentialsException("Consumer already authenticated");
        }
    }

    protected Id getAuthenticatedConsumerIdOrThrowException() {
        return authenticationInfo.getCurrentAuthenticatedUserId()
            .orElseThrow(() -> new BadCredentialsException(
                "User not authenticated"));
    }
}
