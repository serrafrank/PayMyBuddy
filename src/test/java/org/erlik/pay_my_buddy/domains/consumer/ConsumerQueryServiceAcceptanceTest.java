package org.erlik.pay_my_buddy.domains.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.erlik.pay_my_buddy.domains.consumer.queries.ConsumerQueryService;
import org.erlik.pay_my_buddy.domains.consumer.queries.ConsumerQueryServiceImpl;
import org.erlik.pay_my_buddy.domains.consumer.queries.FindConsumerByEmailQuery;
import org.erlik.pay_my_buddy.domains.consumer.queries.FindConsumerByIdQuery;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.fake.ConsumerFake;
import org.erlik.pay_my_buddy.fake.TestFaker;
import org.erlik.pay_my_buddy.mock.ConsumerRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ConsumerQueryServiceAcceptanceTest {

    private final ConsumerRepositoryMock consumerRepository = new ConsumerRepositoryMock();
    private ConsumerQueryService consumerQueryService;

    @BeforeEach
    void init() {
        consumerQueryService = new ConsumerQueryServiceImpl(consumerRepository);
    }

    @Test
    @DisplayName("given a consumer exists when I get a consumer by id than the consumer is returned")
    void findConsumerByIdTest() {
        //GIVEN
        final var consumer = ConsumerFake.generateActiveConsumer();

        consumerRepository.createNewConsumer(consumer);

        final var findConsumerByIdQuery = new FindConsumerByIdQuery(consumer.id());

        //WHEN
        final var foundConsumer = consumerQueryService.findConsumerById(findConsumerByIdQuery);

        //THEN
        assertThat(foundConsumer).isNotNull().satisfies(c -> {
            assertThat(c.firstname()).isEqualTo(consumer.firstname());
            assertThat(c.lastname()).isEqualTo(consumer.lastname());
            assertThat(c.emailAddress()).isEqualTo(consumer.emailAddress());
            assertThat(c.password()).isEqualTo(consumer.password());
        });

    }

    @Test
    @DisplayName("Given a consumer exist when I get a consumer with non existing id then it throws a ConsumerNotFoundException")
    void getAConsumerByNonExistsIdThrowsAConsumerNotFoundExceptionTest() {
        //GIVEN
        final var findConsumerByIdQuery = new FindConsumerByIdQuery(new Id());

        //WHEN
        final Executable executable = () -> consumerQueryService.findConsumerById(
            findConsumerByIdQuery);

        //THEN
        assertThrows(ConsumerNotFoundException.class, executable);
    }

    @Test
    @DisplayName("Given a consumer exist when I get this consumer by emailAddress then the consumer is returned")
    void getAConsumerByEmailTest() {
        //GIVEN
        final var consumer = ConsumerFake.generateActiveConsumer();
        consumerRepository.createNewConsumer(consumer);

        final var findConsumerByEmailQuery = new FindConsumerByEmailQuery(consumer.emailAddress()
            .email());

        //WHEN
        final var response = consumerQueryService.findConsumerByEmail(findConsumerByEmailQuery);

        //THEN
        assertThat(response).isNotNull();
        assertThat(response.firstname()).isEqualTo(consumer.firstname());
        assertThat(response.lastname()).isEqualTo(consumer.lastname());
        assertThat(response.emailAddress()).isEqualTo(consumer.emailAddress());
        assertThat(response.password()).isEqualTo(consumer.password());
        assertThat(response.accounts()).isEqualTo(consumer.accounts());
        assertThat(response.friends()).isEqualTo(consumer.friends());
        assertThat(response.isActive()).isEqualTo(consumer.isActive());
    }

    @Test
    @DisplayName("Given a consumer exist when I get a consumer with non existing emailAddress then it throws a ConsumerNotFoundException")
    void getAConsumerByNonExistsEmailThrowsAConsumerNotFoundExceptionTest() {
        //GIVEN
        final var findConsumerByEmailQuery = new FindConsumerByEmailQuery(TestFaker.fake()
            .internet()
            .emailAddress());

        //WHEN
        final Executable executable = () -> consumerQueryService.findConsumerByEmail(
            findConsumerByEmailQuery);

        //THEN
        assertThrows(ConsumerNotFoundException.class, executable);
    }
}

