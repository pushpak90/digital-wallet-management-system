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
public class AddMoneyRequestDto {
    @NotNull(message = "Wallet Id cannot be null")
    private Long walletId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(
        value = "0.01",
        inclusive = true,
        message = ""
    )
    private BigDecimal amount;
}
