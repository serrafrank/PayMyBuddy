package org.erlik.pay_my_buddy.infrastructure.security;

import java.util.ArrayList;
import org.erlik.pay_my_buddy.domains.exceptions.ConsumerNotFoundException;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.repositories.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl
    implements UserDetailsService {

    @Autowired
    private ConsumerRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String addressEmail) throws UsernameNotFoundException {
        final var email = new EmailAddress(addressEmail);

        final var user = userRepository.getConsumerByEmail(email)
                                       .orElseThrow(() -> new ConsumerNotFoundException(email));

        return new User(user.emailAddress().email(),
            user.password().hashedPassword().value(),
            new ArrayList<>());
    }

}