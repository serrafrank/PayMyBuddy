package org.erlik.pay_my_buddy.fake;

import com.github.javafaker.Faker;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.erlik.pay_my_buddy.domains.models.Password;

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

    public static String generateValidPlainTextPassword() {
        List<List<CharSequence>> acceptedCharacters = getExpectedCharacters();

        int minCharNumber = (int) Math.ceil(Password.getMinLength()
                                            / (double) acceptedCharacters.size());
        int maxCharNumber = (int) Math.floor(Password.getMaxLength()
                                             / (double) acceptedCharacters.size());

        StringBuilder randomString = new StringBuilder();
        acceptedCharacters.forEach(charSequence -> randomString.append(randomString(charSequence,
            minCharNumber,
            maxCharNumber)));

        return mixeCharactersFromString(randomString);
    }

    private static String randomString(List<CharSequence> chars, int minLength, int maxLength) {
        Random random = new Random();
        return RandomStringUtils.random(random.nextInt(maxLength - minLength) + minLength,
            String.join("", chars));
    }

    private static String mixeCharactersFromString(StringBuilder string) {
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        while (!string.isEmpty()) {
            var index = random.nextInt(string.length());
            randomString.append(string.charAt(index));
            string.deleteCharAt(index);
        }
        return randomString.toString();
    }

    private static List<List<CharSequence>> getExpectedCharacters() {
        return List.of(Password.getAcceptedLowerCaseChars(),
            Password.getAcceptedUpperCaseChars(),
            Password.getAcceptedDigits(),
            Password.getAcceptedSpecialChars());
    }
}
