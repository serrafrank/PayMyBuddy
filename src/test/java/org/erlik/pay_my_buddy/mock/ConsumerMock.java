package org.erlik.pay_my_buddy.mock;

import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.junit.jupiter.api.Assertions;

public class ConsumerMock {

    public static Consumer create() {
        return new Consumer(TestFaker.fake().name().firstName(),
            TestFaker.fake().name().lastName(),
            TestFaker.fake().internet().emailAddress(),
            TestFaker.randomAlphaNumericString());
    }

    public static Consumer inactive() {
        var consumer = create();

        Assertions.assertFalse(consumer.isActive());
        return consumer;
    }

    public static Consumer active() {

        var consumer = create().activate();

        Assertions.assertTrue(consumer.isActive());
        return consumer;
    }
}
