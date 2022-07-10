package org.erlik.pay_my_buddy.fake;

import com.github.javafaker.Faker;

public class TestFaker {

    public static Faker fake() {
        return new Faker();
    }

    public static String randomString() {
        return randomString(10);
    }

    public static String randomString(int nbr) {
        return fake().regexify("[a-zA-Z0-9]{" + nbr + "}");
    }
}
