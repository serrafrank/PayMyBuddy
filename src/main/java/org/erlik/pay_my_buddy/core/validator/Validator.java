package org.erlik.pay_my_buddy.core.validator;

import java.util.Collection;
import java.util.Optional;
import org.erlik.pay_my_buddy.core.validator.validator_condition.BooleanValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.CollectionValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.NumberValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.ObjectValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.OptionalValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.StringValidatorCondition;

public record Validator<P>(P param) {

    public static <P extends String> StringValidatorCondition of(P param) {
        return new StringValidatorCondition(param);
    }

    public static <P extends Number> NumberValidatorCondition of(P param) {
        return new NumberValidatorCondition(param);
    }

    public static <P extends Collection<?>> CollectionValidatorCondition of(P param) {
        return new CollectionValidatorCondition(param);
    }

    public static <P extends Boolean> BooleanValidatorCondition of(P param) {
        return new BooleanValidatorCondition(param);
    }

    public static <P extends Optional<?>> OptionalValidatorCondition of(P param) {
        return new OptionalValidatorCondition(param);
    }

    public static <P> ObjectValidatorCondition of(P param) {
        return new ObjectValidatorCondition(param);
    }
}
