package org.erlik.pay_my_buddy.core.validator.validator_condition;

public class BooleanValidatorCondition
    extends AbstractValidatorCondition<Boolean> {

    public BooleanValidatorCondition(boolean actual) {
        super(actual);
    }

    public ValidatorResult isTrue() {
        return response(actual);
    }

    public ValidatorResult isFalse() {
        return response(!actual);
    }
}
