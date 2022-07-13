package org.erlik.pay_my_buddy.domains.exceptions;

import lombok.NonNull;
import org.erlik.pay_my_buddy.core.exceptions.BadRequestException;
import org.erlik.pay_my_buddy.domains.models.Account;
import org.erlik.pay_my_buddy.domains.models.Consumer;

public class AccountTypeAlreadyExists
    extends BadRequestException {

    public AccountTypeAlreadyExists(@NonNull Consumer consumer,
                                    @NonNull Account account) {
        super("Account type already exists. Consumer : " + consumer.id() + " , Account type : " +
              account.accountType());
    }
}
