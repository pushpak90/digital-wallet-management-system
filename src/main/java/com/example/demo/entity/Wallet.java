package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "wallet",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Builder.Default
    @DecimalMin(
        value = "0.0",
        inclusive = true,
        message = "Balance connot be negative"
    )
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        unique = true
    )
    private User user;

    @OneToMany(
        mappedBy = "fromWallet",
        cascade = CascadeType.ALL
    )
    @Builder.Default
    private List<Transaction> sentTransactions = new ArrayList<>();

    @OneToMany(
        mappedBy = "toWallet",
        cascade = CascadeType.ALL
    )
    @Builder.Default
    private List<Transaction> receivedTransactions = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        this.createAt = LocalDateTime.now();
        if(this.balance == null){
            this.balance = BigDecimal.ZERO;
        }
    }
}
