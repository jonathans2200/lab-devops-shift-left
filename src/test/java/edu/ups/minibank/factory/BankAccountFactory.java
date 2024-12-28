package edu.ups.minibank.factory;

import edu.ups.minibank.domain.BankAccount;

import java.math.BigDecimal;

public class BankAccountFactory {

    public static BankAccount getSavingAccount(int fixedId, String ownerName, int initialBalance){
        return BankAccount.builder()
                .amount(BigDecimal.valueOf(initialBalance))
                .accountOwnerName(ownerName)
                .id(fixedId)
                .accountType("SAVINGS")
                .build();
    }
}
