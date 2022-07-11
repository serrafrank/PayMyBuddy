package org.erlik.pay_my_buddy.domains.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.erlik.pay_my_buddy.domains.consumer.events.CreateNewConsumerEvent;
import org.erlik.pay_my_buddy.domains.consumer.events.FindConsumerByIdEvent;
import org.erlik.pay_my_buddy.domains.exceptions.EmailAlreadyExistsException;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.HashedPassword;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.fake.ConsumerFake;
import org.erlik.pay_my_buddy.fake.TestFaker;
import org.erlik.pay_my_buddy.mock.ConsumerRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ConsumerServiceAcceptanceTest {

    private final ConsumerRepositoryMock consumerRepository = new ConsumerRepositoryMock();
    private ConsumerService consumerService;

    @BeforeEach
    void init() {
        consumerService = new ConsumerServiceImpl(consumerRepository);
    }

    @Test
    @DisplayName("When I create a new consumer then the consumer is saved")
    void createANewConsumerTest() {
        //GIVEN
        final var firstname = TestFaker.fake().name().firstName();
        final var lastname = TestFaker.fake().name().lastName();
        final var email = TestFaker.fake().internet().emailAddress();
        final var password = TestFaker.generateValidPlainTextPassword();

        final var createNewConsumerEvent = new CreateNewConsumerEvent(
            firstname,
            lastname,
            email,
            password);

        //WHEN
        final var createdConsumerId = consumerService.createConsumer(createNewConsumerEvent);

        //THEN
        assertThat(createdConsumerId).isNotNull().isInstanceOf(Id.class);
        var createdConsumer = consumerRepository.getConsumers().stream()
            .filter(consumer -> consumer.id().equals(createdConsumerId))
            .findFirst();

        assertThat(createdConsumer).isNotNull()
            .isPresent()
            .get().satisfies(consumer -> {
                assertThat(consumer.firstname()).isEqualTo(firstname);
                assertThat(consumer.lastname()).isEqualTo(lastname);
                assertThat(consumer.emailAddress().email()).isEqualTo(email);
                assertThat(consumer.password().matchWith(password)).isTrue();
            });
    }

    @Test
    @DisplayName("When I create a new consumer with an email that already exists then an exception is thrown")
    void createANewConsumerWithAnEmailThatAlreadyExistsTest() {
        //GIVEN
        final var existingConsumer = ConsumerFake.generateConsumer();

        consumerRepository.createNewConsumer(existingConsumer);

        final var createNewConsumerEvent = new CreateNewConsumerEvent(
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            existingConsumer.emailAddress().email(),
            TestFaker.generateValidPlainTextPassword());

        //WHEN
        final Executable executable = () -> consumerService.createConsumer(createNewConsumerEvent);

        //THEN
        assertThrows(EmailAlreadyExistsException.class, executable);
    }

    @Test
    @DisplayName("given a consumer exists when I get a consumer by id than the consumer is returned")
    void findConsumerByIdTest() {
        //GIVEN
        final var firstname = TestFaker.fake().name().firstName();
        final var lastname = TestFaker.fake().name().lastName();
        final var email = TestFaker.fake().internet().emailAddress();
        final var password = TestFaker.generateValidPlainTextPassword();
        final var consumer = new Consumer(
            firstname,
            lastname,
            email,
            HashedPassword.fromPlainText(password));

        consumerRepository.createNewConsumer(consumer);

        final var findConsumerByIdEvent = new FindConsumerByIdEvent(consumer.id());

        //WHEN
        final var foundConsumer = consumerService.findConsumerById(findConsumerByIdEvent);

        //THEN
        assertThat(foundConsumer).isNotNull().satisfies(c -> {
            assertThat(c.firstname()).isEqualTo(firstname);
            assertThat(c.lastname()).isEqualTo(lastname);
            assertThat(c.emailAddress().email()).isEqualTo(email);
            assertThat(c.password().matchWith(password)).isTrue();
        });

    }

}
