package org.erlik.pay_my_buddy.core.exceptions;

public class InternalErrorException
    extends AbstractException {

    public InternalErrorException() {
        super("Internal error");
    }

    public InternalErrorException(String message) {
        super(message);
    }
}
