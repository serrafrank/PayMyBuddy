package org.erlik.pay_my_buddy.domains.models;

import static org.assertj.core.api.Assertions.assertThat;

import org.erlik.pay_my_buddy.fake.EmailAddressFake;
import org.erlik.pay_my_buddy.fake.TestFaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FriendUnitTest {

    @Test
    @DisplayName("when I use a valid email then it not throws an exception")
    void initFriendTest() {
        //GIVEN
        final var id = new Id();
        final var firstname = TestFaker.fake().name().firstName();
        final var lastname = TestFaker.fake().name().lastName();
        final var emailAddress = EmailAddressFake.generateEmail();

        //WHEN
        final var response = new Friend(id, firstname, lastname, emailAddress);

        //THEN
        assertThat(response.id()).isEqualTo(id);
        assertThat(response.firstname()).isEqualTo(firstname);
        assertThat(response.lastname()).isEqualTo(lastname);
        assertThat(response.emailAddress()).isEqualTo(emailAddress);
    }
}
