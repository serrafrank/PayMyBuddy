package org.erlik.payMyBuddy.domains.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.erlik.payMyBuddy.domains.exceptions.ExcludeDomainNameException;
import org.erlik.payMyBuddy.domains.exceptions.InvalidEmailAddressException;

public record EmailAddress(String email,
                           List<String> excludeDomainName)
    implements ValueObject {

    private static final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final List<String> DEFAULT_EXCLUDE_DOMAIN_NAME = Collections.emptyList();

    public EmailAddress {
        if (!isValid(email)) {
            throw new InvalidEmailAddressException(email);
        }

        if (domainNameIsExclude(email, excludeDomainName)) {
            throw new ExcludeDomainNameException(email);
        }
    }

    public EmailAddress(String email) {
        this(email, DEFAULT_EXCLUDE_DOMAIN_NAME);
    }

    public EmailAddress(String email, String... excludeDomainName) {
        this(email, Arrays.stream(excludeDomainName).toList());
    }

    private boolean isValid(String email) {
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean domainNameIsExclude(String email, List<String> excludeDomainName) {
        String domainName = email.split("@")[1];
        return excludeDomainName.contains(domainName);

    }

    public String toString() {
        return email;
    }
}
