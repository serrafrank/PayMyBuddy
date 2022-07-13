package org.erlik.pay_my_buddy.core.validator.validator_condition;

import org.apache.commons.lang3.StringUtils;

public class StringAbstractValidatorCondition
    extends AbstractValidatorCondition<String> {

    public StringAbstractValidatorCondition(String actual) {
        super(actual);
    }

    public ValidatorResult isEmpty() {
        return response(StringUtils.isEmpty(actual));
    }

    public ValidatorResult isBlank() {
        return response(StringUtils.isBlank(actual));
    }

}
