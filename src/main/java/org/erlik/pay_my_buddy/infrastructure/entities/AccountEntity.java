package org.erlik.pay_my_buddy.infrastructure.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.erlik.pay_my_buddy.domains.models.Account;
import org.erlik.pay_my_buddy.domains.models.accounts.AccountType;
import org.erlik.pay_my_buddy.domains.models.accounts.BankAccount;
import org.erlik.pay_my_buddy.domains.models.accounts.ElectronicMoneyAccount;

@Table
@Entity
@Data
@NoArgsConstructor
public class AccountEntity {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Id
    private UUID id;

    @Column
    private AccountType accountType;
    @Column
    private String datas;

    @SneakyThrows
    public AccountEntity(Account account) {
        accountType = account.accountType();
        datas = objectMapper.writeValueAsString(account);
    }

    @SneakyThrows
    public Account toAccount() {
        return switch (accountType) {
            case ELECTRONIC_MONEY_ACCOUNT -> concertTo(ElectronicMoneyAccount.class);
            case BANK_ACCOUNT -> concertTo(BankAccount.class);
        };
    }

    private <R> R concertTo(Class<R> accountClass) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(datas);
        return objectMapper.treeToValue(jsonNode, accountClass);
    }
}
