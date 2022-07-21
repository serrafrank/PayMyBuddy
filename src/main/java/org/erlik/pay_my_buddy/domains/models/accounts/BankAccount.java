package org.erlik.pay_my_buddy.domains.models.accounts;

import org.erlik.pay_my_buddy.domains.models.Account;

public record BankAccount(String IBAN)
    implements Account {

    @Override
    public AccountType accountType() {
        return AccountType.BANK_ACCOUNT;
    }
}
