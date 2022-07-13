package org.erlik.pay_my_buddy.core.validator.validator_condition;

import java.util.Collection;

public class CollectionAbstractValidatorCondition
    extends AbstractValidatorCondition<Collection<?>> {

    public CollectionAbstractValidatorCondition(Collection<?> actual) {
        super(actual);
    }

    public ValidatorResult isEmpty() {
        return response(actual == null || actual.isEmpty());
    }

    public ValidatorResult isNotEmpty() {
        return response(actual != null && !actual.isEmpty());
    }

}
