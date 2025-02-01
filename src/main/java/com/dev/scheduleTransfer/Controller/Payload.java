package com.dev.scheduleTransfer.Controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Payload {
    private String senderAccountId;
    private String recipientAccountId;
    private int transferAmount;
    private long transferDate;
}
