package com.dev.scheduleTransfer.Persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledTransactionRepository extends JpaRepository<ScheduledTransactionEntity, String> {
    List<ScheduledTransactionEntity> findAllBySenderAccountId(String senderAccountId);
}

