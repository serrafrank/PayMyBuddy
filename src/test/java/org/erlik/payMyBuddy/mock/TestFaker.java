package org.erlik.payMyBuddy.mock;

import com.github.javafaker.Faker;

public class TestFaker {

    public static Faker fake() {
        return new Faker();
    }

    public static String randomAlphaNumericString() {
        return randomAlphaNumericString(10);
    }

    public static String randomAlphaNumericString(int nbr) {
        return fake().regexify("[a-z1-9]{" + nbr + "}");
    }
}
