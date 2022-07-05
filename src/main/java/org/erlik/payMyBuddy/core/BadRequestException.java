package org.erlik.payMyBuddy.core;

public class BadRequestException
    extends AbstractException {

    public BadRequestException() {
        super("Bad request");
    }

    public BadRequestException(String message) {
        super(message);
    }
}