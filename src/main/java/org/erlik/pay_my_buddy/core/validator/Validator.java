package org.erlik.pay_my_buddy.core.validator;

import java.util.Collection;
import java.util.Optional;
import org.erlik.pay_my_buddy.core.validator.validator_condition.AbstractValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.BooleanAbstractValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.CollectionAbstractValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.NumberAbstractValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.ObjectAbstractValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.OptionalAbstractValidatorCondition;
import org.erlik.pay_my_buddy.core.validator.validator_condition.StringAbstractValidatorCondition;

public record Validator<P>(P param) {

    public static <P extends String> StringAbstractValidatorCondition of(P param) {
        return new StringAbstractValidatorCondition(param);
    }

    public static <P extends Number> NumberAbstractValidatorCondition of(P param) {
        return new NumberAbstractValidatorCondition(param);
    }

    public static <P extends Collection<?>> CollectionAbstractValidatorCondition of(P param) {
        return new CollectionAbstractValidatorCondition(param);
    }

    public static <P extends Boolean> BooleanAbstractValidatorCondition of(P param) {
        return new BooleanAbstractValidatorCondition(param);
    }

    public static <P extends Optional<?>> OptionalAbstractValidatorCondition of(P param) {
        return new OptionalAbstractValidatorCondition(param);
    }

    public static <P> AbstractValidatorCondition<?> of(P param) {
        return new ObjectAbstractValidatorCondition(param);
    }
}
