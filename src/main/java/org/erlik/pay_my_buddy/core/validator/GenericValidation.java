package org.erlik.pay_my_buddy.core.validator;

import java.util.function.Predicate;
import java.util.function.Supplier;

public record GenericValidation<P>(P param) {

    public static <P> GenericValidation<P> of(P param) {
        return new GenericValidation<>(param);
    }

    public GenericValidationResult condition(Predicate<P> predicate) {
        return new GenericValidationResult(predicate.test(param()));
    }

    record GenericValidationResult(boolean isValid) {

        public boolean isNotValid() {
            return !isValid;
        }

        public <T extends Throwable> boolean orThrow(Supplier<? extends T> exceptionSupplier)
            throws T {
            if (isNotValid()) {
                throw exceptionSupplier.get();
            }
            return true;
        }
    }
}
