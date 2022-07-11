package org.erlik.pay_my_buddy.domains.exceptions;

import lombok.NonNull;
import org.erlik.pay_my_buddy.core.exceptions.ResourceNotFoundException;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.accounts.AccountType;

public class AccountNotFoundException
    extends ResourceNotFoundException {

    public AccountNotFoundException(@NonNull Consumer consumer,
                                    @NonNull AccountType accountType) {
        super("Account type " + accountType + " not exists for the consumer " + consumer.id());
    }
}
