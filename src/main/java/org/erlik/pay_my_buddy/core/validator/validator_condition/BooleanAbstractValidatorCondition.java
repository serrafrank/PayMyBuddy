package org.erlik.pay_my_buddy.core.validator.validator_condition;

public class BooleanAbstractValidatorCondition
    extends AbstractValidatorCondition<Boolean> {


    public BooleanAbstractValidatorCondition(Boolean actual) {
        super(actual);
    }

    public ValidatorResult isTrue() {
        return response(actual);
    }

    public ValidatorResult isFalse() {
        return response(!actual);
    }
}
