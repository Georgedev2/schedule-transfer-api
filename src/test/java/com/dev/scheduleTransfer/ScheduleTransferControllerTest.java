package com.dev.scheduleTransfer;

import com.dev.scheduleTransfer.Controller.ScheduleTransferController;
import com.dev.scheduleTransfer.Dto.ScheduledTransactionDto;
import com.dev.scheduleTransfer.Persistance.ScheduledTransactionEntity;
import com.dev.scheduleTransfer.Service.ScheduledTransactionService;
import com.dev.scheduleTransfer.Service.ScheduledTransactionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ScheduleTransferControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ScheduledTransactionService scheduledTransactionService;

    @Mock
    private ScheduledTransactionUtils scheduledTransactionUtils;

    @InjectMocks
    private ScheduleTransferController scheduleTransferController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(scheduleTransferController).build();
    }


    @Test
    public void testScheduleTransfer_invalidAmount() throws Exception {
        // Arrange
        long amount = -1000L;  // Invalid amount
        long transferDate = System.currentTimeMillis() + 10000L; // Future date

        when(scheduledTransactionUtils.isAFutureDate(transferDate)).thenReturn(true);
        when(scheduledTransactionUtils.amountIsInValid(amount)).thenReturn(false);  // Invalid amount

        // Act & Assert
        mockMvc.perform(post("/scheduleTransfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{ \"senderAccountId\": \"account123\", \"recipientAccountId\": \"account456\", \"transferAmount\": %d, \"transferDate\": %d }", amount, transferDate)))
                .andExpect(status().isBadRequest())  // Expect HTTP 400 (Bad Request)
                .andExpect(content().string("ErrorResponse(message=The scheduled amount must not be negative, and also date provided must be in the future, hasError=true)"));
    }

    @Test
    public void testScheduleTransfer_invalidDate() throws Exception {
        // Arrange
        long amount = 1000L;
        long transferDate = System.currentTimeMillis() - 10000L;  // Date in the past

        when(scheduledTransactionUtils.isAFutureDate(transferDate)).thenReturn(false);  // Invalid date
        when(scheduledTransactionUtils.amountIsInValid(amount)).thenReturn(true);  // Valid amount

        // Act & Assert
        mockMvc.perform(post("/scheduleTransfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{ \"senderAccountId\": \"account123\", \"recipientAccountId\": \"account456\", \"transferAmount\": %d, \"transferDate\": %d }", amount, transferDate)))
                .andExpect(status().isBadRequest())  // Expect HTTP 400 (Bad Request)
                .andExpect(content().string("ErrorResponse(message=The scheduled amount must not be negative, and also date provided must be in the future, hasError=true)"));
    }

    @Test
    public void testFetchAllScheduleTransactionByAccountId_validAccount() throws Exception {
        // Arrange
        String accountId = "account123";
        ScheduledTransactionEntity scheduledTransactionEntity = new ScheduledTransactionEntity();
        scheduledTransactionEntity.setSenderAccountId(accountId);
        scheduledTransactionEntity.setRecipientAccountId("account456");
        scheduledTransactionEntity.setAmount(1000L);
        scheduledTransactionEntity.setTransferDate(System.currentTimeMillis() + 10000L);
        List<ScheduledTransactionEntity> transactions = Collections.singletonList(scheduledTransactionEntity);

        when(scheduledTransactionService.fetchAllScheduleTransactionByAccountId(accountId)).thenReturn(transactions);

        // Act & Assert
        mockMvc.perform(get("/scheduleTransfer/{accountId}", accountId))
                .andExpect(status().isOk())  // Expect HTTP 200 (OK)
                .andExpect(jsonPath("$[0].senderAccountId").value(accountId))
                .andExpect(jsonPath("$[0].recipientAccountId").value("account456"))
                .andExpect(jsonPath("$[0].amount").value(1000L));
    }

    @Test
    public void testFetchAllScheduleTransactionByAccountId_noTransactions() throws Exception {
        // Arrange
        String accountId = "account123";
        when(scheduledTransactionService.fetchAllScheduleTransactionByAccountId(accountId)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/scheduleTransfer/{accountId}", accountId))
                .andExpect(status().isOk())  // Expect HTTP 200 (OK)
                .andExpect(jsonPath("$").isEmpty());  // Expect empty list
    }
}
