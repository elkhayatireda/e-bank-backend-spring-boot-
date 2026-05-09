package org.example.ebankingbackend.web;


import lombok.AllArgsConstructor;
import org.example.ebankingbackend.dtos.BankAccountDTO;
import org.example.ebankingbackend.entities.BankAccount;
import org.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.example.ebankingbackend.mappers.BankAccountMapperImpl;
import org.example.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    // no need for pathvariable here
    @GetMapping("accounts/{accountId}")
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }


    @GetMapping("/accounts")
    public List<BankAccountDTO> listBankAccountDTO(){
       return bankAccountService.bankAccountList();
    }
}
