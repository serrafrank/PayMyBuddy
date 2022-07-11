package org.erlik.pay_my_buddy.fake;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import org.erlik.pay_my_buddy.domains.models.Id;

public class IdFake {

    public static IdFakeBuilder builder() {
        return new IdFakeBuilder();
    }

    public static Id generateId() {
        return builder().build();
    }

    @With
    @Getter
    @AllArgsConstructor
    static class IdFakeBuilder {

        private UUID id;

        private IdFakeBuilder() {
            id = UUID.randomUUID();
        }

        public Id build() {
            return new Id(id);
        }
    }

}
