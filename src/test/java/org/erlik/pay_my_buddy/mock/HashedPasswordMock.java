package org.erlik.pay_my_buddy.mock;

import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.erlik.pay_my_buddy.domains.models.HashedPassword;

public class HashedPasswordMock {

    public static HashedPassword create() {
        return new HashedPassword(generateValidPlainTextPassword());
    }

    public static String generateValidPlainTextPassword() {
        StringBuilder randomString = new StringBuilder()
            .append(randomString(HashedPassword.getAcceptedLowerCaseChars(), 2, 4))
            .append(randomString(HashedPassword.getAcceptedUpperCaseChars(), 2, 4))
            .append(randomString(HashedPassword.getAcceptedDigits(), 2, 4))
            .append(randomString(HashedPassword.getAcceptedSpecialChars(), 2, 4));

        return mixeCharactersFromString(randomString);
    }

    private static String randomString(List<CharSequence> chars, int minLenght, int maxLenght) {
        Random random = new Random();
        return RandomStringUtils.random(random.nextInt(maxLenght - minLenght) + minLenght,
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
}
