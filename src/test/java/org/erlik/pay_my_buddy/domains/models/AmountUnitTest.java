package org.erlik.pay_my_buddy.domains.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;
import javax.money.Monetary;
import javax.money.MonetaryException;
import javax.money.UnknownCurrencyException;
import javax.money.format.AmountFormatQueryBuilder;
import org.javamoney.moneta.format.CurrencyStyle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AmountUnitTest {

    @Test
    @DisplayName("when I init an Amount then it use euros by default")
    void useEurosByDefaultTest() {
        //GIVEN
        final var amountValue = 5.0;
        final var currencyCode = "EUR";

        //WHEN
        final var response = new Amount(amountValue);

        //THEN
        assertThat(response.numericAmount()).isEqualTo(amountValue);
        assertThat(response.currency()).isEqualTo(Monetary.getCurrency(currencyCode));
        assertThat(response.toString()).hasToString("5,00 €");
    }

    @Test
    @DisplayName("given I init an Amount when I specify a currency then it use this currency")
    void initAmountWithSpecificCurrencyTest() {
        //GIVEN
        final var amountValue = 5.0;
        final var currencyCode = "USD";

        //WHEN
        final var response = new Amount(amountValue, currencyCode);

        //THEN
        assertThat(response.numericAmount()).isEqualTo(amountValue);
        assertThat(response.currency()).isEqualTo(Monetary.getCurrency(currencyCode));
        assertThat(response.toString()).hasToString("5,00 $US");
    }

    @Test
    @DisplayName("given I init an Amount when I specify an invalid currency then it throw an UnknownCurrencyException")
    void initAmountWithInvalidCurrencyThrowExceptionTest() {
        //GIVEN
        final var amountValue = 5.0;
        final var invalidCurrencyCode = "AAA";

        //WHEN
        Executable executable = () -> new Amount(amountValue, invalidCurrencyCode);

        //THEN
        assertThrows(UnknownCurrencyException.class, executable);
    }

    @Test
    @DisplayName("given I init an Amount when I specify a custom format then it is correctly formated")
    void initAmountWithSpecificLocaleTest() {
        //GIVEN
        final var amountValue = 55.0;
        final var currencyCode = "EUR";
        final var customFormat = AmountFormatQueryBuilder.
            of(Locale.US).set(CurrencyStyle.NAME).set("pattern", "000.00 ¤").build();

        //WHEN
        final var response = new Amount(amountValue);
        final var formattedResponse = response.format(customFormat);

        //THEN
        assertThat(response.numericAmount()).isEqualTo(amountValue);
        assertThat(response.currency()).isEqualTo(Monetary.getCurrency(currencyCode));
        assertThat(response.toString()).hasToString("55,00 €");
        assertThat(formattedResponse).hasToString("055.00 Euro");
    }

    @Test
    @DisplayName("given I init an Amount when I subtract an amount then it is correctly calculated")
    void subtractAmountTest() {
        //GIVEN
        final var initialAmount = new Amount(50);
        final var subtractAmount = new Amount(5);
        final var expectedAmount = new Amount(45);

        //WHEN
        final var response = initialAmount.subtract(subtractAmount);

        //THEN
        assertThat(response.toString()).hasToString(expectedAmount.toString());
    }

    @Test
    @DisplayName("given I init an Amount when I add an amount then it is correctly calculated")
    void addAmountTest() {
        //GIVEN
        final var initialAmount = new Amount(50);
        final var subtractAmount = new Amount(5);
        final var expectedAmount = new Amount(55);

        //WHEN
        final var response = initialAmount.add(subtractAmount);

        //THEN
        assertThat(response.toString()).hasToString(expectedAmount.toString());
    }

    @Test
    @DisplayName("given I init an Amount when I subtract an amount with a different currency then it throw a MonetaryException")
    void throwMonetaryExceptionTest() {
        //GIVEN
        final var initialAmount = new Amount(50, "EUR");
        final var subtractAmount = new Amount(5, "USD");

        //WHEN
        Executable subtractExecutable = () -> initialAmount.subtract(subtractAmount);
        Executable addExecutable = () -> initialAmount.add(subtractAmount);

        //THEN
        assertThrows(MonetaryException.class, subtractExecutable);
        assertThrows(MonetaryException.class, addExecutable);
    }

    @Test
    @DisplayName(
        "given I init a negative Amount when I test if it's negative then it's return true " +
        "MonetaryException")
    void amountIsNegativeTest() {
        //GIVEN
        final var positiveAmount = new Amount(5);
        final var negativeAmount = new Amount(-5);

        //WHEN
        final var positiveAmountResult = positiveAmount.isNegative();
        final var negativeAmountResult = negativeAmount.isNegative();

        //THEN
        assertThat(positiveAmountResult).isFalse();
        assertThat(negativeAmountResult).isTrue();
    }

    @Test
    @DisplayName("when an amount exists then I can get the currency code")
    void getCurrencyCode() {
        //GIVEN
        final var expectedCurrencyCode = "USD";
        final var amount = new Amount(5, expectedCurrencyCode);

        //WHEN
        final var response = amount.currencyCode();

        //THEN
        assertThat(response).isEqualTo(expectedCurrencyCode);
    }
}
