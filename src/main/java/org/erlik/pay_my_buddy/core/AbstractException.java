package org.erlik.pay_my_buddy.core;

public abstract class AbstractException
    extends RuntimeException {

    protected AbstractException(final String message) {
        super(message);
    }

}
