package com.example.demo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequestDto {
    @NotNull(message = "Sender wallet ID cannot be null")
    private Long fromWalletId;

    @NotNull(message = "Receiver wallet ID cannot be null")
    private Long toWalletId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(
        value = "0.01",
        inclusive = true,
        message = "Amount must be greater than 0"
    )
    private BigDecimal amount;
}
