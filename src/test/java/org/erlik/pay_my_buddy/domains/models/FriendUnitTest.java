package org.erlik.pay_my_buddy.domains.models;

import java.util.UUID;
import org.erlik.pay_my_buddy.mock.EmailAddressMock;
import org.erlik.pay_my_buddy.mock.TestFaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FriendUnitTest {

    @Test
    @DisplayName("when I use a valid email then it not throws an exception")
    void initFriendTest() {
        //GIVEN
        final var id = UUID.randomUUID();
        final var firstname = TestFaker.fake().name().firstName();
        final var lastname = TestFaker.fake().name().lastName();
        final var emailAddress = EmailAddressMock.create();

        //WHEN
        final var response = new Friend(id, firstname, lastname, emailAddress);

        //THEN
        Assertions.assertEquals(id, response.id());
        Assertions.assertEquals(firstname, response.firstname());
        Assertions.assertEquals(lastname, response.lastname());
        Assertions.assertEquals(emailAddress, response.emailAddress());
    }
}
