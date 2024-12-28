package edu.ups.minibank.service;


import edu.ups.minibank.exceptions.InsufficientFundsException;
import edu.ups.minibank.exceptions.UnrecognizedAccountException;

import java.math.BigDecimal;

public interface BankAccountService {
    void transferBetweenBankAccounts(
            int accountOriginId,
            int accountDestinyId,
            BigDecimal amount
    ) throws InsufficientFundsException, UnrecognizedAccountException;
}
