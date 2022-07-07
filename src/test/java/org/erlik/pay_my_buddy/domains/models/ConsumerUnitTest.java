package org.erlik.pay_my_buddy.domains.models;

import org.erlik.pay_my_buddy.domains.exceptions.InvalidEmailAddressException;
import org.erlik.pay_my_buddy.mock.ConsumerMock;
import org.erlik.pay_my_buddy.mock.TestFaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ConsumerUnitTest {

    @Test
    @DisplayName("when I create a new consumer then it is inactive")
    void newConsumerIsInactiveTest() {
        //GIVEN
        String firstname = TestFaker.fake().name().firstName();
        String lastname = TestFaker.fake().name().lastName();
        String login = TestFaker.fake().internet().emailAddress();
        String password = TestFaker.randomAlphaNumericString();

        //WHEN
        final var consumer = new Consumer(firstname, lastname, login, password);

        //THEN
        Assertions.assertEquals(firstname, consumer.firstname());
        Assertions.assertEquals(lastname, consumer.lastname());
        Assertions.assertEquals(login, consumer.emailAddress().toString());
        Assertions.assertEquals(password, consumer.password());
        Assertions.assertEquals(new Account(), consumer.account());
        Assertions.assertTrue(consumer.friends().isEmpty());
        Assertions.assertFalse(consumer.isActive());
    }

    @Test
    @DisplayName("when I update an inactive consumer then it is active")
    public void updateConsumerToActiveTest() {
        //GIVEN
        final var consumer = ConsumerMock.inactive();
        Assertions.assertFalse(consumer.isActive());

        //WHEN
        final var activatedConsumer = consumer.activate();

        //THEN
        Assertions.assertEquals(consumer.firstname(), activatedConsumer.firstname());
        Assertions.assertEquals(consumer.lastname(), activatedConsumer.lastname());
        Assertions.assertEquals(consumer.emailAddress(), activatedConsumer.emailAddress());
        Assertions.assertEquals(consumer.password(), activatedConsumer.password());
        Assertions.assertEquals(consumer.account(), activatedConsumer.account());
        Assertions.assertTrue(consumer.friends().isEmpty());
        Assertions.assertTrue(activatedConsumer.isActive());
    }

    @Test
    @DisplayName("when I update an active consumer then it is inactive")
    public void updateConsumerToInactiveTest() {
        //GIVEN
        final var consumer = ConsumerMock.active();
        Assertions.assertTrue(consumer.isActive());

        //WHEN
        final var inactivatedConsumer = consumer.inactivate();

        //THEN
        Assertions.assertEquals(consumer.firstname(), inactivatedConsumer.firstname());
        Assertions.assertEquals(consumer.lastname(), inactivatedConsumer.lastname());
        Assertions.assertEquals(consumer.emailAddress(), inactivatedConsumer.emailAddress());
        Assertions.assertEquals(consumer.password(), inactivatedConsumer.password());
        Assertions.assertEquals(consumer.account(), inactivatedConsumer.account());
        Assertions.assertTrue(consumer.friends().isEmpty());
        Assertions.assertFalse(inactivatedConsumer.isActive());
    }

    @Test
    @DisplayName("when I use an invalid email then it throws an InvalidEmailAddressException")
    void initConsumerWithInvalidEmailThrowInvalidEmailAddressExceptionTest() {
        //GIVEN
        final var invalidEmail = "invalid@email";

        //WHEN
        Executable executable = () -> new Consumer("firstname",
            "lastname",
            invalidEmail,
            "password");

        //THEN
        Assertions.assertThrows(InvalidEmailAddressException.class, executable);
    }
}
