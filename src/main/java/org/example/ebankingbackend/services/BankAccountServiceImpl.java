package org.example.ebankingbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ebankingbackend.dtos.*;
import org.example.ebankingbackend.entities.*;
import org.example.ebankingbackend.enums.OperationType;
import org.example.ebankingbackend.exceptions.BalanceNotSufficcientException;
import org.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.example.ebankingbackend.exceptions.CustomerNotFoundException;
import org.example.ebankingbackend.mappers.BankAccountMapperImpl;
import org.example.ebankingbackend.repositories.AccountOperationRepository;
import org.example.ebankingbackend.repositories.BankAccountRepository;
import org.example.ebankingbackend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
//    Logger log = LoggerFactory.getLogger(this.getClass().getName()); using lombok @slf4j

    private BankAccountMapperImpl bankAccountMapper;


    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {

        // regle metier
        Customer customer = bankAccountMapper.fromCustomerDto(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerId, double overDraft) throws CustomerNotFoundException {
        Customer customer =  customerRepository.findById(customerId).orElse(null);
        if (customer == null){
            throw  new CustomerNotFoundException("Customer not Found ");
        }
        CurrentAccount bankAccount = new CurrentAccount();

        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setOverDraft(overDraft);
        CurrentAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.fromCurrentAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerId, double interestRate) throws CustomerNotFoundException {
        Customer customer =  customerRepository.findById(customerId).orElse(null);
        if (customer == null){
            throw  new CustomerNotFoundException("Customer not Found ");
        }
        SavingAccount bankAccount= new SavingAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setInterestRate(interestRate);
        SavingAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return bankAccountMapper.fromSavingAccount(savedBankAccount);
    }


    @Override
    public List<CustomerDto> listCustomers() {
        List<Customer> customers  = customerRepository.findAll();
        List<CustomerDto> customerDtos  = customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDtos;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException{
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                        .orElseThrow(()->new BankAccountNotFoundException(""));
       if (bankAccount instanceof SavingAccount){
           SavingAccount savingAccount = (SavingAccount) bankAccount;
           return  bankAccountMapper.fromSavingAccount(savingAccount);
       }else{
           CurrentAccount currentAccount = (CurrentAccount) bankAccount;
           return  bankAccountMapper.fromCurrentAccount(currentAccount);
       }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficcientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException(""));
        if(bankAccount.getBalance()< amount){
            throw new BalanceNotSufficcientException("Balance not enough ");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws  BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException(""));
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BalanceNotSufficcientException, BankAccountNotFoundException {
        debit(accountIdSource,amount,"debit (transfer)");
        credit(accountIdDestination,amount,"credit (transfer)");
    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccountDTO> bankAccountList = bankAccountRepository.findAll().stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount){
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingAccount(savingAccount);
            }else{
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountList;
    }

    @Override
    public CustomerDto getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("customer not found"));
        CustomerDto customerDto = bankAccountMapper.fromCustomer(customer);
        return customerDto;
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {

        // regle metier
        Customer customer = bankAccountMapper.fromCustomerDto(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO> accountOperationDTOList(String accountId){
        List<AccountOperationDTO> accountOperationDTOList = accountOperationRepository.findByBankAccountId(accountId).stream().map(accountOperation ->
            bankAccountMapper.fromAccountOperation(accountOperation)
        ).collect(Collectors.toList());
        return accountOperationDTOList;
    }
}
