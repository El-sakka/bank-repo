package com.sample.bank.repository;

import com.sample.bank.model.entity.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TransferRepository extends JpaRepository<TransferHistory,Long> {
    @Query("SELECT t FROM TransferHistory t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId")
    List<TransferHistory> findByFromAccountIdOrToAccountId(Long accountId);
}
