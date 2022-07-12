package org.erlik.pay_my_buddy.fake;

import java.util.UUID;
import lombok.Builder;
import org.erlik.pay_my_buddy.domains.models.Id;

@Builder
public class IdFake {

    private UUID id;

    public static Id generateId() {
        return builder().build();
    }


    public static class IdFakeBuilder {

        private IdFakeBuilder() {
            id = UUID.randomUUID();
        }

        public Id build() {
            return new Id(id);
        }
    }

}
