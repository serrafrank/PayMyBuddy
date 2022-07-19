package org.erlik.pay_my_buddy.core.validator.validator_condition;

import java.util.Optional;

public class OptionalValidatorCondition
    extends AbstractValidatorCondition<Optional<?>> {

    public OptionalValidatorCondition(Optional<?> actual) {
        super(actual);
    }

    public ValidatorResult isPresent() {
        return response(actual.isPresent());
    }

    public ValidatorResult isEmpty() {
        return response(actual.isEmpty());
    }
}
