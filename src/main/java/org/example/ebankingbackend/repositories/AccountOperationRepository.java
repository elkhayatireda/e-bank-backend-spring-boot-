package org.example.ebankingbackend.repositories;

import org.example.ebankingbackend.entities.AccountOperation;
import org.example.ebankingbackend.entities.BankAccount;
import org.example.ebankingbackend.enums.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccountId(String accountId);
    Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);

    Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);
    @Query("SELECT SUM(o.amount) FROM AccountOperation o WHERE o.type = :type")
    Double sumByType(@Param("type") OperationType type);
}
