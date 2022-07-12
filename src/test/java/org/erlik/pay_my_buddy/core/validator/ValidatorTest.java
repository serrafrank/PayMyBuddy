package org.erlik.pay_my_buddy.core.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ValidatorTest {

    @Test
    void throwsValidateWhenInitWithOk() {
        //GIVEN
        final String parameter = "String";
        final Predicate<String> rightPredicate = x -> true;

        //WHEN
        var result = Validator.of(parameter)
                              .is(rightPredicate);

        //THEN
        assertThat(result.isValid()).isTrue();
        assertThat(result.isNotValid()).isFalse();
    }

    @Test
    void throwsValidateWhenInitWithFail() {
        //GIVEN
        final var parameter = "String";
        final Predicate<String> wrongPredicate = x -> false;

        //WHEN
        var result = Validator.of(parameter)
                              .is(wrongPredicate);

        //THEN
        assertThat(result.isValid()).isFalse();
        assertThat(result.isNotValid()).isTrue();
    }

    @Test
    void shouldThrowAnExceptionExceptionWhenValueIsTrue() {
        //GIVEN
        final var parameter = "String";
        final Predicate<String> predicate = x -> true;
        final var messageException = "Throw Exception";

        //WHEN
        Executable executable = () -> Validator.of(parameter)
                                               .is(predicate)
                                               .thenThrow(() -> new Exception(messageException));

        //THEN
        var message = assertThrows(Exception.class, executable);

        assertThat(message).hasMessage(messageException);
    }

    @Test
    void shouldThrowAnExceptionExceptionWhenValueIsFalse() {
        //GIVEN
        final var parameter = "String";
        final Predicate<String> predicate = x -> false;
        final var messageException = "Throw Exception";

        //WHEN
        Executable executable = () -> Validator.of(parameter)
                                               .is(predicate)
                                               .orThrow(() -> new Exception(messageException));

        //THEN
        var message = assertThrows(Exception.class, executable);

        assertThat(message).hasMessage(messageException);
    }

    @Test
    void notShouldThrowAnExceptionWhenValueIsTrue() {
        //GIVEN
        final var parameter = "String";
        final Predicate<String> rightPredicate = x -> true;
        final var messageException = "Throw Exception";

        //WHEN
        Executable executable = () -> Validator.of(parameter)
                                               .is(rightPredicate)
                                               .orThrow(() -> new Exception(messageException));

        //THEN
        assertDoesNotThrow(executable);

    }

    @Test
    void notShouldThrowAnExceptionWhenValueIsFalse() {
        //GIVEN
        final var parameter = "String";
        final Predicate<String> rightPredicate = x -> false;
        final var messageException = "Throw Exception";

        //WHEN
        Executable executable = () -> Validator.of(parameter)
                                               .is(rightPredicate)
                                               .thenThrow(() -> new Exception(messageException));

        //THEN
        assertDoesNotThrow(executable);
    }
}
