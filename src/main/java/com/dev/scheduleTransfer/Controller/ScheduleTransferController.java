package com.dev.scheduleTransfer.Controller;

import com.dev.scheduleTransfer.Dto.ScheduledTransactionDto;
import com.dev.scheduleTransfer.Persistance.ScheduledTransactionEntity;
import com.dev.scheduleTransfer.Persistance.ScheduledTransactionRepository;
import com.dev.scheduleTransfer.Service.ScheduledTransactionService;
import com.dev.scheduleTransfer.Service.ScheduledTransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// 6hrs in minute 480, to millis= new Date(480*60*1000+ Date.now())
// new Date(480*60*1000+ Date.now()).getTime()// 1738420274924

// time in the past Date.now()-60*30*1000 ~> 1738399120977

@RestController
public class ScheduleTransferController {
    @Autowired
    private ScheduledTransactionUtils scheduledTransactionUtils;
    @Autowired
    private ScheduledTransactionService scheduledTransactionService;


    @PostMapping("/scheduleTransfer")
    public ResponseEntity<?> scheduleTransfer(@RequestBody Payload payload) {

        long amount = payload.getTransferAmount();
        long transferDate = payload.getTransferDate();

        Boolean isFutureDate = scheduledTransactionUtils.isAFutureDate(transferDate);
        Boolean isValidAmount = scheduledTransactionUtils.amountIsInValid(amount);

        if (isValidAmount && isFutureDate) {

            ScheduledTransactionDto scheduledTransactionDto = new ScheduledTransactionDto( payload.getSenderAccountId(),payload.getRecipientAccountId(), amount, transferDate);
            return ResponseEntity.status(201).body(scheduledTransactionService.scheduledTransaction(scheduledTransactionDto));

        } else {
            return ResponseEntity.status(400).body(new ErrorResponse("The scheduled amount must not be negative, and also date provided must be in the future", true).toString());
        }
    }

    @GetMapping("/scheduleTransfer/{accountId}")
    public ResponseEntity<List<ScheduledTransactionEntity>> fetchAllScheduleTransactionByAccountId(@PathVariable String accountId) {
        List<ScheduledTransactionEntity> result = scheduledTransactionService.fetchAllScheduleTransactionByAccountId(accountId);
        return ResponseEntity.status(200).body(result);

    }

}
