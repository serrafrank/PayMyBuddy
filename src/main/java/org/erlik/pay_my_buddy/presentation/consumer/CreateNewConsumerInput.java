package org.erlik.pay_my_buddy.presentation.consumer;

public record CreateNewConsumerInput(String firstname,
                                     String lastname,
                                     String emailAddress,
                                     String password) {

}
