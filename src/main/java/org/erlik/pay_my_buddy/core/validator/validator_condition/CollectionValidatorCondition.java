package org.erlik.pay_my_buddy.core.validator.validator_condition;

import java.util.Collection;

public class CollectionValidatorCondition
    extends AbstractValidatorCondition<Collection<?>> {

    public CollectionValidatorCondition(Collection<?> actual) {
        super(actual);
    }

    public ValidatorResult isEmpty() {
        return response(actual == null || actual.isEmpty());
    }

    public ValidatorResult isNotEmpty() {
        return response(actual != null && !actual.isEmpty());
    }

}
