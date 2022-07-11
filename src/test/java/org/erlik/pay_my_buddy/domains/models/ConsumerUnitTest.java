package org.erlik.pay_my_buddy.domains.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import org.erlik.pay_my_buddy.domains.exceptions.InvalidEmailAddressException;
import org.erlik.pay_my_buddy.domains.models.accounts.ElectronicMoneyAccount;
import org.erlik.pay_my_buddy.mock.ConsumerMock;
import org.erlik.pay_my_buddy.mock.HashedPasswordMock;
import org.erlik.pay_my_buddy.mock.TestFaker;
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
        String password = HashedPasswordMock.generateValidPlainTextPassword();
        HashedPassword hashedPassword = HashedPassword.fromPlainText(password);

        //WHEN
        final var consumer = new Consumer(firstname, lastname, login, hashedPassword);

        //THEN
        assertThat(consumer.firstname()).isEqualTo(firstname);
        assertThat(consumer.lastname()).isEqualTo(lastname);
        assertThat(consumer.emailAddress().toString()).hasToString(login);
        assertThat(consumer.password()).isEqualTo(hashedPassword);
        assertThat(hashedPassword.matchWith(password)).isTrue();
        assertThat(consumer.accounts()).isEqualTo(Set.of(new ElectronicMoneyAccount()));
        assertThat(consumer.friends()).isEmpty();
        assertThat(consumer.isActive()).isFalse();
    }

    @Test
    @DisplayName("when I update an inactive consumer then it is active")
    void updateConsumerToActiveTest() {
        //GIVEN
        final var consumer = ConsumerMock.inactive();
        assertThat(consumer.isActive()).isFalse();

        //WHEN
        final var activatedConsumer = consumer.activate();

        //THEN
        assertThat(activatedConsumer.firstname()).isEqualTo(consumer.firstname());
        assertThat(activatedConsumer.lastname()).isEqualTo(consumer.lastname());
        assertThat(activatedConsumer.emailAddress()).isEqualTo(consumer.emailAddress());
        assertThat(activatedConsumer.password()).isEqualTo(consumer.password());
        assertThat(activatedConsumer.accounts()).isEqualTo(consumer.accounts());
        assertThat(consumer.friends().isEmpty()).isTrue();
        assertThat(activatedConsumer.isActive()).isTrue();
    }

    @Test
    @DisplayName("when I update an active consumer then it is inactive")
    void updateConsumerToInactiveTest() {
        //GIVEN
        final var consumer = ConsumerMock.active();
        assertThat(consumer.isActive()).isTrue();

        //WHEN
        final var inactivatedConsumer = consumer.inactivate();

        //THEN
        assertThat(inactivatedConsumer.firstname()).isEqualTo(consumer.firstname());
        assertThat(inactivatedConsumer.lastname()).isEqualTo(consumer.lastname());
        assertThat(inactivatedConsumer.emailAddress()).isEqualTo(consumer.emailAddress());
        assertThat(inactivatedConsumer.password()).isEqualTo(consumer.password());
        assertThat(inactivatedConsumer.accounts()).isEqualTo(consumer.accounts());
        assertThat(consumer.friends().isEmpty()).isTrue();
        assertThat(inactivatedConsumer.isActive()).isFalse();
    }

    @Test
    @DisplayName("when I use an invalid email then it throws an InvalidEmailAddressException")
    void initConsumerWithInvalidEmailThrowInvalidEmailAddressExceptionTest() {
        //GIVEN
        final var invalidEmail = "invalid@email";

        //WHEN
        Executable executable = () -> new Consumer(TestFaker.randomString(),
            TestFaker.randomString(),
            invalidEmail,
            HashedPassword.fromPlainText(HashedPasswordMock.generateValidPlainTextPassword()));

        //THEN
        assertThrows(InvalidEmailAddressException.class, executable);
    }
}
