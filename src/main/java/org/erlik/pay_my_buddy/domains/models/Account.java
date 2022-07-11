package org.erlik.pay_my_buddy.domains.models;

import org.erlik.pay_my_buddy.domains.models.accounts.AccountType;

public interface Account
    extends ValueObject {

    AccountType accountType();

}
