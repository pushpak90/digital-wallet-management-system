package com.example.demo.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddMoneyRequestDto;
import com.example.demo.dto.TransactionResponseDto;
import com.example.demo.dto.TransferRequestDto;
import com.example.demo.dto.WalletResponseDto;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionStatus;
import com.example.demo.entity.User;
import com.example.demo.entity.Wallet;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.InsufficientBalanceException;
import com.example.demo.exception.InvalidTransactionException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;
import com.example.demo.service.WalletService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public WalletResponseDto createWallet(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found by ID : " + userId));

        if (walletRepository.existsByUserId(userId)) {
            throw new DuplicateResourceException("Wallet already exists for this user");
        }

        Wallet wallet = Wallet.builder().user(user).balance(BigDecimal.ZERO).build();

        Wallet savedWallet = walletRepository.save(wallet);

        WalletResponseDto responseDto = modelMapper.map(savedWallet, WalletResponseDto.class);

        return responseDto;
    }

    @Override
    public WalletResponseDto getWalletById(Long walletId) {

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with ID : " + walletId));

        WalletResponseDto responseDto = modelMapper.map(wallet, WalletResponseDto.class);

        responseDto.setUserId(wallet.getUser().getId());

        return responseDto;
    }

    @Override
    public WalletResponseDto addMoney(AddMoneyRequestDto requestDto) {
        Wallet wallet = walletRepository.findById(requestDto.getWalletId())
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(requestDto.getAmount()));

        Wallet updateWallet = walletRepository.save(wallet);

        WalletResponseDto responseDto = modelMapper.map(updateWallet, WalletResponseDto.class);

        responseDto.setUserId(wallet.getUser().getId());

        return responseDto;
    }

    @Override
    @Transactional
    public TransactionResponseDto transferMoney(TransferRequestDto requestDto) {

        if (requestDto.getFromWalletId().equals(requestDto.getToWalletId())) {
            throw new InvalidTransactionException("Sender and receiver wallets cannot be same");
        }

        Wallet senderWallet = walletRepository.findById(requestDto.getFromWalletId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository.findById(requestDto.getToWalletId())
                .orElseThrow(() -> new ResourceNotFoundException("Reciver wallet not found"));

        if (senderWallet.getBalance().compareTo(requestDto.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient wallet balance");
        }

        senderWallet.setBalance(senderWallet.getBalance().subtract(requestDto.getAmount()));

        receiverWallet.setBalance(receiverWallet.getBalance().add(requestDto.getAmount()));

        walletRepository.save(senderWallet);

        walletRepository.save(receiverWallet);

        Transaction transaction = Transaction.builder().fromWallet(senderWallet).toWallet(receiverWallet)
                .amount(requestDto.getAmount()).status(TransactionStatus.SUCCESS).build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        TransactionResponseDto responseDto = new TransactionResponseDto();

        responseDto.setTransactionId(
                savedTransaction.getTransactionId());

        responseDto.setFromWalletId(
                senderWallet.getWalletId());

        responseDto.setToWalletId(
                receiverWallet.getWalletId());

        responseDto.setAmount(
                savedTransaction.getAmount());

        responseDto.setTransactionTime(
                savedTransaction.getTransactionTime());

        responseDto.setStatus(
                savedTransaction.getStatus());

        return responseDto;
    }

    @Override
    public List<TransactionResponseDto> getWalletTransactions(Long walletId) {

        walletRepository.findById(walletId).orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        List<Transaction> transactions = transactionRepository.findByFromWalletWalletIdOrToWalletWalletId(walletId,
                walletId);

        return transactions.stream()
                .map(
                        transaction -> {
                            TransactionResponseDto dto = new TransactionResponseDto();

                            dto.setTransactionId(transaction.getTransactionId());
                            dto.setFromWalletId(transaction.getFromWallet().getWalletId());
                            dto.setToWalletId(transaction.getToWallet().getWalletId());
                            dto.setAmount(transaction.getAmount());
                            dto.setTransactionTime(transaction.getTransactionTime());
                            dto.setStatus(transaction.getStatus());

                            return dto;
                        })
                .toList();
    }

}
