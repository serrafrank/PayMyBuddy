package org.erlik.pay_my_buddy.core.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.Predicate;
import org.erlik.pay_my_buddy.core.validator.GenericValidation.GenericValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class GenericValidationTest {

    @Test
    void throwsValidateWhenInitWithOk() {
        //GIVEN
        final var parameter = "String";
        final Predicate<String> rightPredicate = x -> true;

        //WHEN
        var result = GenericValidation.of(parameter)
            .condition(rightPredicate);

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
        var result = GenericValidation.of(parameter)
            .condition(wrongPredicate);

        //THEN
        assertThat(result.isValid()).isFalse();
        assertThat(result.isNotValid()).isTrue();
    }

    @Test
    void shouldThrowAnExceptionExceptionWhenValueIsFalse() {
        //GIVEN
        final var parameter = "String";
        final Predicate<String> predicate = x -> false;
        final var messageException = "Throw Exception";

        //WHEN
        Executable executable = () -> GenericValidation.of(parameter)
            .condition(predicate)
            .orThrow(() -> new Exception(messageException));

        //THEN
        var message = assertThrows(Exception.class, executable);

        assertThat(message).hasMessage(messageException);
    }

    @Test
    void notShouldThrowAnExceptionWhenValueIsTrue() throws Exception {
        var e = new GenericValidationResult(true).orThrow(() -> new Exception("Throw Exception"));
        assertThat(e).isTrue();

        //GIVEN
        final var parameter = "String";
        final Predicate<String> rightPredicate = x -> true;
        final var messageException = "Throw Exception";

        //WHEN
        Executable executable = () -> GenericValidation.of(parameter)
            .condition(rightPredicate)
            .orThrow(() -> new Exception(messageException));

        //THEN
        assertDoesNotThrow(executable);

    }
}
