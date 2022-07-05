package org.erlik.payMyBuddy.domains.models;

import java.util.Locale;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQuery;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import org.javamoney.moneta.format.CurrencyStyle;

public record Amount(Number numericAmount,
                     CurrencyUnit currency)
    implements ValueObject {

    public static final String DEFAULT_CURRENCY = "EUR";
    public static final Locale DEFAULT_LOCALE = Locale.FRENCH;

    public Amount(Number amount) {
        this(amount, DEFAULT_CURRENCY);
    }

    public Amount(MonetaryAmount monetaryAmount) {
        this(monetaryAmount.getNumber(), monetaryAmount.getCurrency());
    }

    public Amount(Number amount, String currency) {
        this(amount, Monetary.getCurrency(currency));
    }

    public MonetaryAmount monetaryAmount() {
        return Monetary.getDefaultAmountFactory()
                       .setCurrency(currency)
                       .setNumber(numericAmount)
                       .create();
    }

    public String format(AmountFormatQuery amountFormatQuery) {
        MonetaryAmountFormat monetaryAmountFormat = MonetaryFormats.getAmountFormat(
            amountFormatQuery);
        return monetaryAmountFormat
            .format(monetaryAmount())
            .replaceAll(" ", " ");
    }

    public Amount add(Amount amount) {
        var result = monetaryAmount().add(amount.monetaryAmount());
        return new Amount(result);
    }

    public Amount subtract(Amount amount) {
        var result = monetaryAmount().subtract(amount.monetaryAmount());
        return new Amount(result);
    }

    public boolean isNegative() {
        return monetaryAmount().isNegative();
    }

    @Override
    public String toString() {
        var amountFormatQuery =
            AmountFormatQueryBuilder.of(DEFAULT_LOCALE)
                                    .set(CurrencyStyle.SYMBOL)
                                    .build();
        return format(amountFormatQuery);
    }
}
