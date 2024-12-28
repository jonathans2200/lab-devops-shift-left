package edu.ups.minibank.service;


import edu.ups.minibank.domain.BankAccount;
import edu.ups.minibank.exceptions.InsufficientFundsException;
import edu.ups.minibank.repository.BankAccountRepository;
import edu.ups.minibank.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @InjectMocks
    private BankAccountServiceImpl bankAccountServiceImplMock;

    @Mock
    private BankAccountRepository bankAccountRepositoryMock;

    @Test
    @DisplayName("BAI_CA1: When call transferBetweenBankAccounts with enough funds then it should run ok")
    void whenCallTransferBetweenBankAccountsShouldRunOk() {
        //@Given
        BankAccount accountOrigin = BankAccount
                .builder()
                .id(1)
                .amount(BigDecimal.valueOf(1000))
                .build();

        BankAccount accountDestiny = BankAccount
                .builder()
                .id(2)
                .amount(BigDecimal.valueOf(100))
                .build();

        BigDecimal transferAmount = BigDecimal.valueOf(500);
        when(bankAccountRepositoryMock.findById(1))
                .thenReturn(Optional.of(accountOrigin));
        when(bankAccountRepositoryMock.findById(2))
                .thenReturn(Optional.of(accountDestiny));
        when(bankAccountRepositoryMock.saveAll(anyIterable()))
                .thenReturn(anyList());
        //@When


        //@Then
        assertDoesNotThrow(()->bankAccountServiceImplMock.transferBetweenBankAccounts(
                1,
                2,
                transferAmount));
    }

    @Test
    @DisplayName("BAI_CA2: When call transferBetweenBankAccounts with NOT enough funds then it should throw an Exception")
    void whenCallTransferBetweenBankAccountsShouldThrowEx(){
        //@Given
        BankAccount accountOrigin = BankAccount
                .builder()
                .id(1)
                .amount(BigDecimal.valueOf(100))
                .build();

        BankAccount accountDestiny = BankAccount
                .builder()
                .id(2)
                .amount(BigDecimal.valueOf(100))
                .build();

        BigDecimal transferAmount = BigDecimal.valueOf(500);
        //@When
        when(bankAccountRepositoryMock.findById(1))
                .thenReturn(Optional.of(accountOrigin));
        when(bankAccountRepositoryMock.findById(2))
                .thenReturn(Optional.of(accountDestiny));

        //@Then
        assertThrows(InsufficientFundsException.class, ()->bankAccountServiceImplMock.transferBetweenBankAccounts(
                1,
                2,
                transferAmount
        ));
        verify(bankAccountRepositoryMock, never()).saveAll(anyIterable());
    }
}
