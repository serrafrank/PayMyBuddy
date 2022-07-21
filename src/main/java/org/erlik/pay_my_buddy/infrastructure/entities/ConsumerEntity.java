package org.erlik.pay_my_buddy.infrastructure.entities;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.erlik.pay_my_buddy.domains.models.Consumer;
import org.erlik.pay_my_buddy.domains.models.EmailAddress;
import org.erlik.pay_my_buddy.domains.models.Friend;
import org.erlik.pay_my_buddy.domains.models.Id;
import org.erlik.pay_my_buddy.domains.models.Password;
import org.erlik.pay_my_buddy.domains.models.Password.HashedPassword;

@Table
@Entity
@Data
@NoArgsConstructor
public class ConsumerEntity {

    @javax.persistence.Id
    private UUID id;

    private String firstname;
    private String lastname;

    private String emailAddress;

    private String password;

    @OneToMany
    private List<AccountEntity> accounts;

    @OneToMany
    private List<ConsumerEntity> friends;

    private boolean isActive;

    public ConsumerEntity(Consumer consumer) {
        this.id = consumer.id().id();
        this.firstname = consumer.firstname();
        this.lastname = consumer.lastname();
        this.emailAddress = consumer.emailAddress().email();
        this.password = consumer.password().hashedPassword().value();
        this.accounts = consumer.accounts().stream().map(AccountEntity::new).toList();
        this.friends = consumer.friends().stream().map(ConsumerEntity::new).toList();
        this.isActive = consumer.isActive();
    }

    public ConsumerEntity(Friend friend) {
        this.id = friend.id().id();
    }

    public Consumer toConsumer() {
        return new Consumer(
            new Id(this.id),
            this.firstname,
            this.lastname,
            new EmailAddress(this.emailAddress),
            new Password(new HashedPassword(this.password)),
            this.accounts.stream().map(AccountEntity::toAccount).toList(),
            this.friends.stream().map(ConsumerEntity::toFriend).toList(),
            this.isActive
        );
    }

    public Friend toFriend() {
        return new Friend(
            new Id(this.id),
            this.firstname,
            this.lastname,
            new EmailAddress(this.emailAddress)
        );
    }
}
