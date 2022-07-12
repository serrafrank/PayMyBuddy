package org.erlik.pay_my_buddy.core.validator.validator_condition;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class AbstractValidatorCondition<T> {

    protected T actual;

    protected AbstractValidatorCondition(T actual) {
        this.actual = actual;
    }

    public ValidatorResult is(Predicate<T> predicate) {
        return response(predicate.test(actual));
    }

    public ValidatorResult isEqualTo(String string) {
        return response(actual.equals(string));
    }

    public ValidatorResult isNull() {
        return response(Objects.isNull(actual));
    }

    protected ValidatorResult response(Boolean r) {
        return new ValidatorResult(r);
    }

    public record ValidatorResult(boolean isValid) {

        public boolean isNotValid() {
            return !isValid;
        }

        public <T extends Throwable> void orThrow(Supplier<? extends T> exceptionSupplier)
            throws T {
            if (isNotValid()) {
                throw exceptionSupplier.get();
            }
        }

        public <T extends Throwable> void thenThrow(Supplier<? extends T> exceptionSupplier)
            throws T {
            if (isValid()) {
                throw exceptionSupplier.get();
            }
        }

        public void thenThrow(String message) throws IllegalArgumentException {
            thenThrow(() -> new IllegalArgumentException(message));
        }

        public void thenThrow() throws IllegalArgumentException {
            thenThrow(IllegalArgumentException::new);
        }
    }

}
