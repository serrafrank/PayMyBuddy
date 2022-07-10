package org.erlik.pay_my_buddy.fake;

import static org.assertj.core.api.Assertions.assertThat;

import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.HashedPassword;

public class ConsumerFake {

    public static Consumer generateConsumer() {

        final var hashedPassword = HashedPassword.fromPlainText(HashedPasswordFake.generateValidPlainTextPassword());

        return new Consumer(
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            TestFaker.fake().internet().emailAddress(),
            hashedPassword);
    }

    public static Consumer inactive() {
        final var consumer = generateConsumer();

        assertThat(consumer.isActive()).isFalse();
        return consumer;
    }

    public static Consumer active() {
        final var consumer = generateConsumer().activate();

        assertThat(consumer.isActive()).isTrue();
        return consumer;
    }
}
