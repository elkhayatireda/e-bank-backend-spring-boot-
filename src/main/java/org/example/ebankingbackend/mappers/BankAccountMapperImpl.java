package org.example.ebankingbackend.mappers;

import org.example.ebankingbackend.dtos.CurrentBankAccountDTO;
import org.example.ebankingbackend.dtos.CustomerDto;
import org.example.ebankingbackend.dtos.SavingBankAccountDTO;
import org.example.ebankingbackend.entities.CurrentAccount;
import org.example.ebankingbackend.entities.Customer;
import org.example.ebankingbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class BankAccountMapperImpl {

    public CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDTO = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
    public Customer fromCustomerDto(CustomerDto customerDto ){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
    }
    public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }
    public CurrentAccount fromCurrentAccountDto(CurrentBankAccountDTO currentBankAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDto(currentBankAccountDTO.getCustomerDto()));
        return currentAccount;
    }

    public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;
    }
    public SavingAccount fromSavingAccountDto(SavingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDto(savingBankAccountDTO.getCustomerDto()));
        return savingAccount;
    }



}
