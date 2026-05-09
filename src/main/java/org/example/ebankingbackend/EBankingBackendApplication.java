package org.example.ebankingbackend;

import org.example.ebankingbackend.entities.*;
import org.example.ebankingbackend.enums.AccountStatus;
import org.example.ebankingbackend.enums.OperationType;
import org.example.ebankingbackend.exceptions.BalanceNotSufficcientException;
import org.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.example.ebankingbackend.exceptions.CustomerNotFoundException;
import org.example.ebankingbackend.repositories.AccountOperationRepository;
import org.example.ebankingbackend.repositories.BankAccountRepository;
import org.example.ebankingbackend.repositories.CustomerRepository;
import org.example.ebankingbackend.services.BankAccountService;
import org.example.ebankingbackend.services.BankAccountServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            Stream.of("Reda", "Imane", "Mohamed").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(cust->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000, cust.getId() ,9000);
                    bankAccountService.saveSavingBankAccount(Math.random()*90000, cust.getId() ,5.5);
                    List<BankAccount> bankAccountList = bankAccountService.bankAccountList();
                    for(BankAccount bankAccount:bankAccountList){
                        for (int i = 0 ; i< 10 ; i++){
                            bankAccountService.credit(bankAccount.getId(),Math.random()*120000,"credit ");
                            bankAccountService.debit(bankAccount.getId(),Math.random()*120000,"debit ");
                        }
                    }

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                } catch (BankAccountNotFoundException e) {
                    e.printStackTrace();
                } catch (BalanceNotSufficcientException e) {
                    e.printStackTrace();
                }

            });
        };
    }
}

