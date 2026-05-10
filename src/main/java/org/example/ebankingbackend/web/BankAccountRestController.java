package org.example.ebankingbackend.web;


import lombok.AllArgsConstructor;
import org.example.ebankingbackend.dtos.AccountHistoryDTO;
import org.example.ebankingbackend.dtos.AccountOperationDTO;
import org.example.ebankingbackend.dtos.BankAccountDTO;
import org.example.ebankingbackend.entities.BankAccount;
import org.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.example.ebankingbackend.mappers.BankAccountMapperImpl;
import org.example.ebankingbackend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    // no need for pathvariable here
    @GetMapping("accounts/{accountId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }


    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<BankAccountDTO> listBankAccountDTO(){
       return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountOperationDTOList(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public AccountHistoryDTO  getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name = "page" , defaultValue = "0") int page ,
            @RequestParam(name = "page" , defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
}
