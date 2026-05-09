package org.example.ebankingbackend.services;

import org.example.ebankingbackend.dtos.BankAccountDTO;
import org.example.ebankingbackend.dtos.CurrentBankAccountDTO;
import org.example.ebankingbackend.dtos.CustomerDto;
import org.example.ebankingbackend.dtos.SavingBankAccountDTO;
import org.example.ebankingbackend.entities.BankAccount;
import org.example.ebankingbackend.entities.CurrentAccount;
import org.example.ebankingbackend.entities.Customer;
import org.example.ebankingbackend.entities.SavingAccount;
import org.example.ebankingbackend.exceptions.BalanceNotSufficcientException;
import org.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.example.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDto saveCustomer(CustomerDto customerDto);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException;
    List<CustomerDto> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficcientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination, double amount)throws BankAccountNotFoundException, BalanceNotSufficcientException;

    List<BankAccountDTO> bankAccountList();

    CustomerDto getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDto updateCustomer(CustomerDto customerDto);

    void deleteCustomer(Long customerId);
}
