package org.erlik.pay_my_buddy.core.exceptions;

import java.lang.reflect.Type;
import lombok.NonNull;
import org.erlik.pay_my_buddy.domains.models.Id;

public class ResourceNotFoundException
    extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource cannot be found");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(@NonNull Id id) {
        super("Resource with id = " + id + " cannot be found.");
    }

    public ResourceNotFoundException(@NonNull Id id,
                                     @NonNull Type type) {
        super("Resource " + type + " with id = " + id + " cannot be found.");
    }

}
