package com.example.wallet.controller;

import com.example.wallet.service.WalletService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet/")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @PostMapping
    public ResponseEntity<Void> performOperation(@RequestBody WalletOperationRequest request) {
        walletService.performOperation(request.getWalletId(), request.getOperationType(), request.getAmount());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{walletId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID walletId) {
        BigDecimal balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }
    @Setter
    @Getter
    public static class WalletOperationRequest {
        private UUID walletId;
        private String operationType;
        private BigDecimal amount;

    }
}
