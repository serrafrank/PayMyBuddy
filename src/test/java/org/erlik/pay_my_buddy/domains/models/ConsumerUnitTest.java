package org.erlik.pay_my_buddy.domains.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.erlik.pay_my_buddy.domains.exceptions.FriendAlreadyExistsException;
import org.erlik.pay_my_buddy.domains.exceptions.InvalidEmailAddressException;
import org.erlik.pay_my_buddy.domains.models.accounts.ElectronicMoneyAccount;
import org.erlik.pay_my_buddy.fake.ConsumerFake;
import org.erlik.pay_my_buddy.fake.FriendFake;
import org.erlik.pay_my_buddy.fake.PasswordFake;
import org.erlik.pay_my_buddy.fake.TestFaker;
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
        String password = TestFaker.generateValidPlainTextPassword();
        Password hashedPassword = new Password(password);

        //WHEN
        final var consumer = new Consumer(firstname, lastname, login, hashedPassword);

        //THEN
        assertThat(consumer.firstname()).isEqualTo(firstname);
        assertThat(consumer.lastname()).isEqualTo(lastname);
        assertThat(consumer.emailAddress().toString()).hasToString(login);
        assertThat(consumer.password()).isEqualTo(hashedPassword);
        assertThat(hashedPassword.matchWith(password)).isTrue();
        assertThat(consumer.accounts()).isEqualTo(List.of(new ElectronicMoneyAccount()));
        assertThat(consumer.friends()).isEmpty();
        assertThat(consumer.isActive()).isFalse();
    }

    @Test
    @DisplayName("given a consumer when I add a non existing friend it is added to the list")
    void addFriendTest() {
        //GIVEN
        Consumer consumer = ConsumerFake.generateActiveConsumer();

        Friend friend = FriendFake.generateFriend();

        //WHEN
        var response = consumer.addFriend(friend);

        //THEN
        assertThat(response.friends()).isNotEmpty();
        assertThat(response.friends()).containsExactly(friend);
    }

    @Test
    @DisplayName("given a consumer when I add an already existing friend then it throws an exception")
    void addNonExistingFriendTest() {
        //GIVEN
        Consumer consumer = ConsumerFake.generateActiveConsumer();

        Friend friend = FriendFake.generateFriend();
        Consumer consumerWithFriend = consumer.addFriend(friend);

        //WHEN
        Executable executable = () -> consumerWithFriend.addFriend(friend);

        //THEN
        assertThrows(FriendAlreadyExistsException.class, executable);

    }

    @Test
    @DisplayName("when I update an inactive consumer then it is active")
    void updateConsumerToActiveTest() {
        //GIVEN
        final var consumer = ConsumerFake.generateInactiveConsumer();
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
        final var consumer = ConsumerFake.generateActiveConsumer();
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
            PasswordFake.generateHashedPassword());

        //THEN
        assertThrows(InvalidEmailAddressException.class, executable);
    }
}
