package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.AddMoneyRequestDto;
import com.example.demo.dto.TransactionResponseDto;
import com.example.demo.dto.TransferRequestDto;
import com.example.demo.dto.WalletResponseDto;

public interface WalletService {
    
    WalletResponseDto createWallet(Long userId);
    
    WalletResponseDto getWalletById(Long walletId);

    WalletResponseDto addMoney(AddMoneyRequestDto requestDto);

    TransactionResponseDto transferMoney(
        TransferRequestDto requestDto
    );

    List<TransactionResponseDto> getWalletTransactions(
        Long walletId
    );
}
