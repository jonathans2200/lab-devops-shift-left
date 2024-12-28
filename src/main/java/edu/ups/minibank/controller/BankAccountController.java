package edu.ups.minibank.controller;

import edu.ups.minibank.controller.dto.TransferRequestDTO;
import edu.ups.minibank.exceptions.InsufficientFundsException;
import edu.ups.minibank.exceptions.UnrecognizedAccountException;
import edu.ups.minibank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<Void> executeTransfer(
            @PathVariable int accountId,
            @RequestBody TransferRequestDTO transferRequestDTO) {

        try{
            bankAccountService.transferBetweenBankAccounts(
                    accountId,
                    transferRequestDTO.destinyAccountId(),
                    transferRequestDTO.amount()
            );

            return status(HttpStatus.OK).build();
        }
        catch(UnrecognizedAccountException | InsufficientFundsException ex){
            return status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
