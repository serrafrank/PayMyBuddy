package org.erlik.payMyBuddy.core.exceptions;

import lombok.NonNull;

public class ResourceNotFoundException
    extends AbstractException {

    public ResourceNotFoundException() {
        super("Resource cannot be found");
    }

    public ResourceNotFoundException(@NonNull String msg) {
        super(msg);
    }

}