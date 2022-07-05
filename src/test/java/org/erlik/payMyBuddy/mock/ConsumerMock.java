package org.erlik.payMyBuddy.mock;

import org.erlik.payMyBuddy.domains.models.Consumer;
import org.junit.jupiter.api.Assertions;

public class ConsumerMock {

    public static Consumer inactive() {
      var consumer = new Consumer(TestFaker.fake().name().firstName(),
          TestFaker.fake().name().lastName(),
          TestFaker.fake().internet().emailAddress(),
          TestFaker.randomAlphaNumericString());

        Assertions.assertFalse(consumer.isActive());
        return consumer;
    }

    public static Consumer active() {
      var consumer = new Consumer(TestFaker.fake().name().firstName(),
          TestFaker.fake().name().lastName(),
          TestFaker.fake().internet().emailAddress(),
          TestFaker.randomAlphaNumericString())
          .active();

        Assertions.assertTrue(consumer.isActive());
        return consumer;
    }
}
