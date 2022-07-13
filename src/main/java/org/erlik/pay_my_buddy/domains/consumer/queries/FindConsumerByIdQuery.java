package org.erlik.pay_my_buddy.domains.consumer.queries;

import org.erlik.pay_my_buddy.domains.Query;
import org.erlik.pay_my_buddy.domains.models.Id;

public record FindConsumerByIdQuery(Id id)
    implements Query {

}
