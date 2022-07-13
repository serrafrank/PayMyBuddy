package org.erlik.pay_my_buddy.domains.transaction.commands;

import org.erlik.pay_my_buddy.domains.Command;
import org.erlik.pay_my_buddy.domains.models.Id;

public record CreateNewTransactionCommand(Id debtor,
                                          Id creditor,
                                          Number amount,
                                          String currency)
    implements Command {

}
