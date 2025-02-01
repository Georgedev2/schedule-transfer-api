package com.dev.scheduleTransfer.Persistance;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="scheduled_transfers")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledTransactionEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    public String transferId;
    @Column
    public String senderAccountId;
    @Column
    public String recipientAccountId;
    @Column
    public Long amount;
    @Column
    public Long transferDate;

}
