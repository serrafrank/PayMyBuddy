package org.erlik.pay_my_buddy.fake;

import lombok.Builder;
import org.erlik.pay_my_buddy.domains.models.Amount;

@Builder
public class AmountFake {

    private Number amount;
    private String currency;

    public static Amount generateAmount() {
        return builder().build();
    }

    public static class AmountFakeBuilder {

        private AmountFakeBuilder() {
            amount = 100;
            currency = "EUR";
        }

        public Amount build() {
            return new Amount(amount, currency);
        }
    }


}
