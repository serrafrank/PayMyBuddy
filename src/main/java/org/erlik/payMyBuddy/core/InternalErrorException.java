package org.erlik.payMyBuddy.core;

public class InternalErrorException
    extends AbstractException {

    public InternalErrorException() {
        super("Internal error");
    }

    public InternalErrorException(String message) {
        super(message);
    }
}