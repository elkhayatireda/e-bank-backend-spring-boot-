package org.example.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ebankingbackend.entities.BankAccount;
import org.example.ebankingbackend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private double amount;
    private Date operationDate;
    private OperationType type;
    private String description;
}
