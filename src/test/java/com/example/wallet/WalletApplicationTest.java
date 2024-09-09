package com.example.wallet;

import com.example.wallet.controller.WalletController;
import com.example.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WalletApplicationTest {

    private MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }

    @Test
    void testPerformOperationDeposit() throws Exception {
        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(1000);
        String json = String.format("{\"walletId\":\"%s\",\"operationType\":\"DEPOSIT\",\"amount\":%s}", walletId, amount);

        mockMvc.perform(post("/api/v1/wallet/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(walletService, times(1)).performOperation(walletId, "DEPOSIT", amount);
    }

    @Test
    void testPerformOperationWithdraw() throws Exception {
        UUID walletId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(500);
        String json = String.format("{\"walletId\":\"%s\",\"operationType\":\"WITHDRAW\",\"amount\":%s}", walletId, amount);

        mockMvc.perform(post("/api/v1/wallet/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(walletService, times(1)).performOperation(walletId, "WITHDRAW", amount);
    }

}