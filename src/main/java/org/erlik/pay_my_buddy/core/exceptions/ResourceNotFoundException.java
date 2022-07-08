package org.erlik.pay_my_buddy.core.exceptions;

import lombok.NonNull;

public class ResourceNotFoundException
    extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource cannot be found");
    }

    public ResourceNotFoundException(@NonNull String id) {
        super("Resource with id = " + id + " cannot be found.");
    }

}
