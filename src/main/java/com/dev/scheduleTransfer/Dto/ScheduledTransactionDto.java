package com.dev.scheduleTransfer.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ScheduledTransactionDto {
    public String senderAccountId;
    public String recipientAccountId;
    public Long amount;
    public Long transferDate;

}
