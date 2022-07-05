package org.erlik.payMyBuddy.core;

import java.util.Locale;
import javax.money.Monetary;
import javax.money.MonetaryException;
import javax.money.UnknownCurrencyException;
import javax.money.format.AmountFormatQueryBuilder;
import org.erlik.payMyBuddy.domains.models.Amount;
import org.javamoney.moneta.format.CurrencyStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class AmountUnitTest {

    @Test
    @DisplayName("when I init an Amount then it use euros by default")
    public void useEurosByDefault() {
        //GIVEN
        var amountValue = 5.0;
        var currencyCode = "EUR";

        //WHEN
        var response = new Amount(amountValue);

        //THEN
        Assertions.assertEquals(amountValue, response.numericAmount());
        Assertions.assertEquals(Monetary.getCurrency(currencyCode), response.currency());
        Assertions.assertEquals("5,00 €", response.toString());
    }

    @Test
    @DisplayName("given I init an Amount when I specify a currency then it use this currency")
    public void initAmountWithSpecificCurrency() {
        //GIVEN
        var amountValue = 5.0;
        var currencyCode = "USD";

        //WHEN
        var response = new Amount(amountValue, currencyCode);

        //THEN
        Assertions.assertEquals(amountValue, response.numericAmount());
        Assertions.assertEquals(Monetary.getCurrency(currencyCode), response.currency());
        Assertions.assertEquals("5,00 $US", response.toString());
    }

    @Test
    @DisplayName("given I init an Amount when I specify an invalid currency then it throw an UnknownCurrencyException")
    public void initAmountWithInvalidCurrencyThrowException() {
        //GIVEN
        var amountValue = 5.0;
        var invalidCurrencyCode = "AAA";

        //WHEN
        Executable executable = () -> new Amount(amountValue, invalidCurrencyCode);

        //THEN
        Assertions.assertThrows(UnknownCurrencyException.class, executable);
    }

    @Test
    @DisplayName("given I init an Amount when I specify a custom format then it is correctly formated")
    public void initAmountWithSpecificLocale() {
        //GIVEN
        var amountValue = 55.0;
        var currencyCode = "EUR";
        var customFormat = AmountFormatQueryBuilder.
            of(Locale.US).set(CurrencyStyle.NAME).set("pattern", "000.00 ¤").build();

        //WHEN
        var response = new Amount(amountValue);
        var formattedResponse = response.format(customFormat);

        //THEN
        Assertions.assertEquals(amountValue, response.numericAmount());
        Assertions.assertEquals(Monetary.getCurrency(currencyCode), response.currency());
        Assertions.assertEquals("55,00 €", response.toString());
        Assertions.assertEquals("055.00 Euro", formattedResponse);
    }

    @Test
    @DisplayName("given I init an Amount when I subtract an amount then it is correctly calculated")
    public void subtractAmount() {
        //GIVEN
        var initialAmount = new Amount(50);
        var subtractAmount = new Amount(5);
        var expectedAmount = new Amount(45);

        //WHEN
        var response = initialAmount.subtract(subtractAmount);

        //THEN
        Assertions.assertEquals(expectedAmount.toString(), response.toString());
    }

    @Test
    @DisplayName("given I init an Amount when I add an amount then it is correctly calculated")
    public void addAmount() {
        //GIVEN
        var initialAmount = new Amount(50);
        var subtractAmount = new Amount(5);
        var expectedAmount = new Amount(55);

        //WHEN
        var response = initialAmount.add(subtractAmount);

        //THEN
        Assertions.assertEquals(expectedAmount.toString(), response.toString());
    }

    @Test
    @DisplayName("given I init an Amount when I subtract an amount with a different currency then it throw a MonetaryException")
    public void throwMonetaryException() {
        //GIVEN
        var initialAmount = new Amount(50, "EUR");
        var subtractAmount = new Amount(5, "USD");

        //WHEN
        Executable subtractExecutable = () -> initialAmount.subtract(subtractAmount);
        Executable addExecutable = () -> initialAmount.add(subtractAmount);

        //THEN
        Assertions.assertThrows(MonetaryException.class, subtractExecutable);
        Assertions.assertThrows(MonetaryException.class, addExecutable);
    }

    @Test
    @DisplayName(
        "given I init a negative Amount when I test if it's negative then it's return true " +
            "MonetaryException")
    public void amountIsNegative() {
        //GIVEN
        var positiveAmount = new Amount(5);
        var negativeAmount = new Amount(-5);

        //WHEN
        var positiveAmountResult = positiveAmount.isNegative();
        var negativeAmountResult = negativeAmount.isNegative();

        //THEN
        Assertions.assertFalse(positiveAmountResult);
        Assertions.assertTrue(negativeAmountResult);
    }
}
