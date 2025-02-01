package com.dev.scheduleTransfer.Service;

import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class ScheduledTransactionUtils {
    public Boolean isAFutureDate(long date) {
        long currentTime = new Date().getTime();
        if (date > currentTime) {
            return true;
        }
        return false;
    }

    public Boolean amountIsInValid(long amount) {
        if (amount > 0) {
            return true;
        }
        return false;
    }
}
