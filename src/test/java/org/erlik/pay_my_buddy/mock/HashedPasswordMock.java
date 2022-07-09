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
        List<List<CharSequence>> acceptedCharacters = getExpectedCharacters();

        int minCharNumber = (int) Math.ceil(HashedPassword.getMinLength()
                                            / (double) acceptedCharacters.size());
        int maxCharNumber = (int) Math.floor(HashedPassword.getMaxLength()
                                             / (double) acceptedCharacters.size());

        StringBuilder randomString = new StringBuilder();
        acceptedCharacters.forEach(charSequence -> randomString.append(randomString(charSequence,  minCharNumber, maxCharNumber)));

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
        return List.of(HashedPassword.getAcceptedLowerCaseChars(),
            HashedPassword.getAcceptedUpperCaseChars(),
            HashedPassword.getAcceptedDigits(),
            HashedPassword.getAcceptedSpecialChars());
    }
}
