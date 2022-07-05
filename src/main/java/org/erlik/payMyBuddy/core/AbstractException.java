package org.erlik.payMyBuddy.core;


public abstract class AbstractException
    extends RuntimeException {

    public AbstractException() {
        super();
    }

    public AbstractException(final String message,
                             final Throwable cause) {
        super(message, cause);
    }

    public AbstractException(final String message) {
        super(message);
    }

    public AbstractException(final Throwable cause) {
        super(cause);
    }
}