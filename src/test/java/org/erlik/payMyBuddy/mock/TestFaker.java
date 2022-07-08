package org.erlik.payMyBuddy.mock;

import com.github.javafaker.Faker;
import java.util.Random;

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

    public static String validPassword() {
        StringBuilder randomString = new StringBuilder()
            .append(fake().regexify("[a-z]{2,4}"))
            .append(fake().regexify("[A-Z]{2,4}"))
            .append(fake().regexify("[0-9]{2,4}"))
            .append(fake().regexify("[!@#$%^&*]{2,4}"));

        StringBuilder password = new StringBuilder();
        Random random = new Random();

        while (!randomString.isEmpty()) {
            var index = random.nextInt(randomString.length());
            password.append(randomString.charAt(index));
            randomString.deleteCharAt(index);
        }

        return password.toString();
    }


}
