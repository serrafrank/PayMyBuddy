package org.erlik.pay_my_buddy.core.validator.validator_condition;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class CollectionValidatorConditionTest {

    @Test
    void emptyCollectionTest() {
        //GIVEN
        var isEmpty = List.of();

        //WHEN
        var response = new CollectionValidatorCondition(isEmpty).isEmpty();

        //THEN
        assertThat(response.isValid()).isTrue();
        assertThat(response.isNotValid()).isFalse();
    }

    @Test
    void notEmptyCollectionTest() {
        //GIVEN
        var isEmpty = List.of("notEmpty");

        //WHEN
        var response = new CollectionValidatorCondition(isEmpty).isNotEmpty();

        //THEN
        assertThat(response.isValid()).isTrue();
        assertThat(response.isNotValid()).isFalse();
    }
}