package org.erlik.payMyBuddy.mock;

import org.erlik.payMyBuddy.domains.models.Consumer;
import org.erlik.payMyBuddy.domains.models.HashedPassword;
import org.junit.jupiter.api.Assertions;

public class ConsumerMock {

    public static Consumer create() {
        final var hashedPassword = HashedPassword.fromPlainText(TestFaker.validPassword());
        return new Consumer(
            TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            TestFaker.fake().internet().emailAddress(),
            hashedPassword);
    }

    public static Consumer inactive() {
        var consumer = create();

        Assertions.assertFalse(consumer.isActive());
        return consumer;
    }

    public static Consumer active() {

        var consumer = create().active();

        Assertions.assertTrue(consumer.isActive());
        return consumer;
    }
}
