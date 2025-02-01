package com.dev.scheduleTransfer.Service;

import com.dev.scheduleTransfer.Dto.ScheduledTransactionDto;
import com.dev.scheduleTransfer.Persistance.ScheduledTransactionEntity;
import com.dev.scheduleTransfer.Persistance.ScheduledTransactionRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledTransactionService {

    private final ScheduledTransactionRepository scheduledTransactionRepository;

    @Autowired
    public ScheduledTransactionService(ScheduledTransactionRepository scheduledTransactionRepository) {
        this.scheduledTransactionRepository = scheduledTransactionRepository;
    }

    public List<ScheduledTransactionEntity> fetchAllScheduleTransactionByAccountId(String accountId) {
        if (accountId != null) {
            return scheduledTransactionRepository.findAllBySenderAccountId(accountId);
        }
        return List.of();
    }

    public  Optional<ScheduledTransactionEntity> scheduledTransaction(ScheduledTransactionDto transferObj) {
        if (transferObj != null) {
            ScheduledTransactionEntity scheduledTransactionEntity = ScheduledTransactionEntity.builder()
                    .senderAccountId(transferObj.getSenderAccountId())
                    .recipientAccountId(transferObj.getRecipientAccountId())
                    .amount(transferObj.getAmount())
                    .transferDate(transferObj.getTransferDate())
                    .build();

             scheduledTransactionRepository.save(scheduledTransactionEntity);
             return Optional.of(scheduledTransactionEntity);
        }
        return Optional.empty();
    }

}
