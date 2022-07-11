package org.erlik.pay_my_buddy.domains.models.transactions;

public enum ConsumerType {
    DEBTOR("debtor"),
    CREDIOR("creditor");

    public final String type;

    ConsumerType(String label) {
        this.type = label;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
