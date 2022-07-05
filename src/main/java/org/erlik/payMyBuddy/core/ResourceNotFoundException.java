package org.erlik.payMyBuddy.core;

import lombok.NonNull;

public class ResourceNotFoundException
    extends AbstractException {

    public ResourceNotFoundException() {
        super("Resource cannot be found");
    }

    public ResourceNotFoundException(@NonNull String id) {
        super("Resource with id = " + id + " cannot be found.");
    }

}