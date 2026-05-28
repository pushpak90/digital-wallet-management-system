package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AddMoneyRequestDto;
import com.example.demo.dto.TransactionResponseDto;
import com.example.demo.dto.TransferRequestDto;
import com.example.demo.dto.WalletResponseDto;
import com.example.demo.service.WalletService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<WalletResponseDto> createWallet(@PathVariable Long userId){
        WalletResponseDto responseDto = walletService.createWallet(userId);
        return new ResponseEntity<>(
            responseDto,
            HttpStatus.CREATED
        );
    }
    
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponseDto> getWalletbyId(@PathVariable Long walletId){
        WalletResponseDto responseDto = walletService.getWalletById(walletId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/add-money")
    public ResponseEntity<WalletResponseDto> addMoney(@Valid @RequestBody AddMoneyRequestDto requestDto){
        WalletResponseDto responseDto = walletService.addMoney(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> transferMoney(@Valid @RequestBody TransferRequestDto requestDto){
        TransactionResponseDto responsedDto = walletService.transferMoney(requestDto);
        return ResponseEntity.ok(responsedDto);
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseEntity<List<TransactionResponseDto>> getWalletTransaction(@PathVariable Long walletId){
        List<TransactionResponseDto> transactions = walletService.getWalletTransactions(walletId);
        return ResponseEntity.ok(transactions);
    }
}
