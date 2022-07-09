package org.erlik.pay_my_buddy.mock;

import static org.assertj.core.api.Assertions.assertThat;

import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.HashedPassword;

public class ConsumerMock {

    public static Consumer create() {

        final var hashedPassword = HashedPassword.fromPlainText(HashedPasswordMock.generateValidPlainTextPassword());

        return new Consumer(
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            TestFaker.fake().internet().emailAddress(),
            hashedPassword);
    }

    public static Consumer inactive() {
        final var consumer = create();

        assertThat(consumer.isActive()).isFalse();
        return consumer;
    }

    public static Consumer active() {
        final var consumer = create().activate();

        assertThat(consumer.isActive()).isTrue();
        return consumer;
    }
}
