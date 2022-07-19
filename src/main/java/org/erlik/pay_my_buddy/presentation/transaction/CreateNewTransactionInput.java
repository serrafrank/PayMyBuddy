package org.erlik.pay_my_buddy.presentation.transaction;

import org.erlik.pay_my_buddy.domains.models.Id;

public record CreateNewTransactionInput(Id creditor,
                                        Number amount,
                                        String currency) {

}
