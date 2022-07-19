package org.erlik.pay_my_buddy.core.validator.validator_condition;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BooleanValidatorConditionTest {

    @Test
    void isTrueTest() {
        //GIVEN
        Boolean isTrue = Boolean.TRUE;

        //WHEN
        var response = new BooleanValidatorCondition(isTrue).isTrue();

        //THEN
        assertThat(response.isValid()).isTrue();
        assertThat(response.isNotValid()).isFalse();
    }

    @Test
    void isFalseTest() {
        //GIVEN
        Boolean isFalse = Boolean.FALSE;

        //WHEN
        var response = new BooleanValidatorCondition(isFalse).isFalse();

        //THEN
        assertThat(response.isValid()).isTrue();
        assertThat(response.isNotValid()).isFalse();
    }
}