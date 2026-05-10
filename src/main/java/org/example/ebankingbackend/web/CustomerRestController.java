package org.example.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ebankingbackend.dtos.CustomerDto;
import org.example.ebankingbackend.entities.Customer;
import org.example.ebankingbackend.exceptions.CustomerNotFoundException;
import org.example.ebankingbackend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<CustomerDto> customers (){
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public CustomerDto getCustomer( @PathVariable(name = "id") Long customerId ) throws CustomerNotFoundException {
        CustomerDto customerDto = bankAccountService.getCustomer(customerId);
        return customerDto;
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){
          return bankAccountService.saveCustomer(customerDto);
    }

    @PutMapping("/customers/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")// no need for pathvariable is they have the same name
    public CustomerDto updateCustomer(Long customerId, @RequestBody CustomerDto customerDto){
        customerDto.setId(customerId);
        return bankAccountService.updateCustomer(customerDto);
    }

    @DeleteMapping("/customers/{customerId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteCustomer(Long customerId){
        bankAccountService.deleteCustomer(customerId);
    }
}
