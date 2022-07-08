package org.erlik.payMyBuddy.domains.models;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.erlik.payMyBuddy.core.PasswordEncoder;
import org.erlik.payMyBuddy.domains.exceptions.PasswordFormatNotValidException;

public record HashedPassword(String hashedPassword) {

    private static final List<String> specialChars = List.of("!",
        "@",
        "#",
        "$",
        "%",
        "^",
        "&",
        "*");
    private static final Integer MIN_LENGTH = 8;
    private static final Integer MAX_LENGTH = 20;
    private static final String UPPER_CASE_CHARS = "(.*[A-Z].*)";
    private static final String LOWER_CASE_CHARS = "(.*[a-z].*)";
    private static final String NUMBERS = "(.*[0-9].*)";
    private static final String SPECIAL_CHARS = "(.*[" + String.join("", specialChars) + "].*$)";


    final static List<Rule> rules = List.of(
        new Rule(password -> password.length() > MIN_LENGTH,
            "Password must be more than " + MIN_LENGTH + " characters in length."),
        new Rule(password -> password.length() < MAX_LENGTH,
            "Password must be less than " + MAX_LENGTH + " characters in length."),
        new Rule(password -> password.matches(UPPER_CASE_CHARS),
            "Password must have at least one uppercase character"),
        new Rule(password -> password.matches(LOWER_CASE_CHARS),
            "Password must have at least one lowercase character"),
        new Rule(password -> password.matches(NUMBERS), "Password must have at least one number"),
        new Rule(password -> password.matches(SPECIAL_CHARS),
            "Password must have at least one special character from " + specialChars)

    );

    public HashedPassword {
        if (StringUtils.isBlank(hashedPassword)) {
            throw new IllegalArgumentException("Hashed password could not be blank");
        }
    }

    public static HashedPassword fromPlainText(String plainTextPassword) {
        var errors = getValidationErrors(plainTextPassword);
        if (!errors.isEmpty()) {
            throw new PasswordFormatNotValidException(plainTextPassword, errors);
        }
        return new HashedPassword(PasswordEncoder.encode(plainTextPassword));
    }

    private static List<String> getValidationErrors(String password) {
        return rules.stream().filter(r -> !r.isValid(password))
                    .map(Rule::error)
                    .collect(Collectors.toList());
    }

    public boolean matchWith(String plainTextPassword) {
        return PasswordEncoder.matches(plainTextPassword, hashedPassword);
    }

    private record Rule(Predicate<String> predicate,
                        String error) {

        public boolean isValid(String string) {
            return predicate.test(string);
        }

    }
}