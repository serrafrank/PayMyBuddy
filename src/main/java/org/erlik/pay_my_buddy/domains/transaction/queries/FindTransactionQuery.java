package org.erlik.pay_my_buddy.domains.transaction.queries;

import org.erlik.pay_my_buddy.domains.Query;
import org.erlik.pay_my_buddy.domains.models.Id;

public record FindTransactionQuery(Id transactionId)
    implements Query {

}
