package edu.ups.minibank.service.impl;

import edu.ups.minibank.domain.BankAccount;
import edu.ups.minibank.exceptions.InsufficientFundsException;
import edu.ups.minibank.exceptions.UnrecognizedAccountException;
import edu.ups.minibank.repository.BankAccountRepository;
import edu.ups.minibank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Override
    public void transferBetweenBankAccounts(
            int accountOriginId,
            int accountDestinyId,
            BigDecimal amount)
            throws InsufficientFundsException, UnrecognizedAccountException {

        BankAccount accountOrigin = getAccount(accountOriginId);
        BankAccount accountDestiny = getAccount(accountDestinyId);

//        Uncomment this code to solve the unit test failures
//        if(accountOrigin.getAmount().subtract(amount).compareTo(BigDecimal.valueOf(0)) < 0){
//            throw new InsufficientFundsException();
//        }

        accountOrigin.setAmount(accountOrigin.getAmount().subtract(amount));
        accountDestiny.setAmount(accountDestiny.getAmount().add(amount));
        bankAccountRepository.saveAll(List.of(accountOrigin, accountDestiny));
    }

    private BankAccount getAccount(int accountId) throws UnrecognizedAccountException {
        Optional<BankAccount> account = bankAccountRepository.findById(accountId);
        if(account.isEmpty()){
            throw new UnrecognizedAccountException();
        }

        return account.get();
    }
}
