package org.erlik.pay_my_buddy.domains.models;

import java.util.Locale;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQuery;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import org.erlik.pay_my_buddy.core.validator.Validator;
import org.javamoney.moneta.format.CurrencyStyle;

/**
 * @param numericAmount - numeric amount of the amount
 * @param currency      - currency of the amount
 */
public record Amount(Number numericAmount,
                     CurrencyUnit currency)
    implements ValueObject {

    public static final String DEFAULT_CURRENCY = "EUR";
    public static final Locale DEFAULT_LOCALE = Locale.FRENCH;

    /**
     * @param numericAmount - numeric amount of the amount
     * @param currency      - currency of the amount
     */
    public Amount {
        Validator.of(numericAmount)
            .isNull()
            .thenThrow("numericAmount could not be null");

        Validator.of(currency)
            .isNull()
            .thenThrow("currency could not be null");
    }

    /**
     * @param numericAmount - numeric amount of the amount
     */
    public Amount(Number numericAmount) {
        this(numericAmount, DEFAULT_CURRENCY);
    }

    /**
     * @param monetaryAmount - monetary amount to convert to amount
     */
    public Amount(MonetaryAmount monetaryAmount) {
        this(monetaryAmount.getNumber(), monetaryAmount.getCurrency());
    }

    /**
     * @param numericAmount - numeric amount of the amount
     * @param currency      - currency of the amount
     */
    public Amount(Number numericAmount, String currency) {
        this(numericAmount, Monetary.getCurrency(currency));
    }

    /**
     * @return - numeric amount of the amount
     */
    public MonetaryAmount monetaryAmount() {
        return Monetary.getDefaultAmountFactory()
            .setCurrency(currency)
            .setNumber(numericAmount)
            .create();
    }

    /**
     * @param amountFormatQuery - amount format query
     * @return - formatted amount
     */
    public String format(AmountFormatQuery amountFormatQuery) {
        MonetaryAmountFormat monetaryAmountFormat = MonetaryFormats.getAmountFormat(
            amountFormatQuery);
        return monetaryAmountFormat
            .format(monetaryAmount())
            .replace("\u00A0", " ");
    }

    /**
     * @param amount - amount to add
     * @return - new amount with added amount
     */
    public Amount add(Amount amount) {
        var result = monetaryAmount().add(amount.monetaryAmount());
        return new Amount(result);
    }

    /**
     * @param amount - amount to subtract
     * @return - new amount with subtracted amount
     */
    public Amount subtract(Amount amount) {
        var result = monetaryAmount().subtract(amount.monetaryAmount());
        return new Amount(result);
    }

    /**
     * @return - true if amount is negative
     */
    public boolean isNegative() {
        return monetaryAmount().isNegative();
    }

    /**
     * @return - currency code of the amount
     */
    public String currencyCode() {
        return currency.getCurrencyCode();
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
