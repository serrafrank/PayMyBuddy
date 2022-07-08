package org.erlik.pay_my_buddy.domains.models;

import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.erlik.pay_my_buddy.core.PasswordEncoder;
import org.erlik.pay_my_buddy.domains.exceptions.PasswordFormatNotValidException;

public record HashedPassword(String hashedPassword) {

    private static final Integer MIN_LENGTH = 8;
    private static final Integer MAX_LENGTH = 20;
    private static final List<CharSequence> UPPER_CASE_CHARS = List.of("A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z");
    private static final List<CharSequence> LOWER_CASE_CHARS = List.of("a",
        "b",
        "c",
        "d",
        "e",
        "f",
        "g",
        "h",
        "i",
        "j",
        "k",
        "l",
        "m",
        "n",
        "o",
        "p",
        "q",
        "r",
        "s",
        "t",
        "u",
        "v",
        "w",
        "x",
        "y",
        "z");
    private static final List<CharSequence> NUMBERS = List.of("0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9");
    private static final List<CharSequence> SPECIAL_CHARS = List.of("!",
        "@",
        "#",
        "$",
        "%",
        "^",
        "&",
        "*",
        "_",
        "-",
        "+",
        "=",
        "|",
        ":",
        ",",
        ".",
        "?");

    private static final List<Rule> rules = List.of(
        new Rule(password -> password.length() >= MIN_LENGTH,
            "Password must be more or equal than " + MIN_LENGTH + " characters in length."),
        new Rule(password -> password.length() <= MAX_LENGTH,
            "Password must be less or equal than " + MAX_LENGTH + " characters in length."),
        new Rule(password -> stringContainsAtLeastOneChar(password, UPPER_CASE_CHARS),
            "Password must have at least one uppercase character"),
        new Rule(password -> stringContainsAtLeastOneChar(password, LOWER_CASE_CHARS),
            "Password must have at least one lowercase character"),
        new Rule(password -> stringContainsAtLeastOneChar(password, NUMBERS),
            "Password must have at least one number"),
        new Rule(password -> stringContainsAtLeastOneChar(password, SPECIAL_CHARS),
            "Password must have at least one special character from " + SPECIAL_CHARS)
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
            .toList();
    }

    public static List<CharSequence> getAcceptedLowerCaseChars() {
        return LOWER_CASE_CHARS;
    }

    public static List<CharSequence> getAcceptedUpperCaseChars() {
        return UPPER_CASE_CHARS;
    }

    public static List<CharSequence> getAcceptedDigits() {
        return NUMBERS;
    }

    public static List<CharSequence> getAcceptedSpecialChars() {
        return SPECIAL_CHARS;
    }

    public static Integer getMinLength() {
        return MIN_LENGTH;
    }

    public static Integer getMaxLength() {
        return MAX_LENGTH;
    }

    private static boolean stringContainsAtLeastOneChar(String string, List<CharSequence> chars) {
        return chars.stream().anyMatch(string::contains);
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
