package org.erlik.pay_my_buddy.domains.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class IdUnitTest {

    @Test
    void testToString() {
        //GIVEN
        var id = UUID.randomUUID();

        //WHEN
        var response = new Id(id);

        //THEN
        assertThat(response.toString()).isNotNull()
            .hasToString(id.toString());
    }

    @Test
    void id() {
        //WHEN
        var response = new Id();

        //THEN
        assertThat(response.id()).isNotNull()
            .isInstanceOf(UUID.class);
    }
}
