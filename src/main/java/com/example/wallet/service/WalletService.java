package com.example.wallet.service;

import com.example.wallet.exception.InsufficientFundsException;
import com.example.wallet.exception.WalletNotFoundException;
import com.example.wallet.model.Wallet;
import com.example.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        return walletRepository.save(wallet);
    }

    @Transactional
    public void performOperation(UUID walletId, String operationType, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() ->
                new WalletNotFoundException("Wallet not found"));
        if (operationType.equalsIgnoreCase("deposit")) {
            wallet.setBalance(wallet.getBalance().add(amount));
        } else if (operationType.equalsIgnoreCase("withdraw")) {
            if (wallet.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds");
            }
            wallet.setBalance(wallet.getBalance().subtract(amount));
        }
        walletRepository.save(wallet);
    }
    public BigDecimal getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() ->
                new WalletNotFoundException("Wallet not found"));
        return wallet.getBalance();
    }
}
