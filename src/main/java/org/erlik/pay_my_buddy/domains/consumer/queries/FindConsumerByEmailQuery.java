package org.erlik.pay_my_buddy.domains.consumer.queries;

import org.erlik.pay_my_buddy.domains.Query;

public record FindConsumerByEmailQuery(String email)
    implements Query {

}
