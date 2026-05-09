package org.example.ebankingbackend.mappers;

import org.example.ebankingbackend.dtos.CustomerDto;
import org.example.ebankingbackend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class BankAccountMapperImpl {

    public CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDTO = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDTO);
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setEmail(customer.getEmail());

        return customerDTO;
    }
    public Customer fromCustomerDto(CustomerDto customerDto ){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto,customer);
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setEmail(customer.getEmail());

        return customer;
    }
}
