package br.com.connmandakaru.mandabank.entity;

import br.com.connmandakaru.mandabank.entity.enums.transactions.TransactionStatus;
import br.com.connmandakaru.mandabank.entity.enums.transactions.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Version
    private Long version;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private User payee;

    @NotNull
    private BigDecimal transactionValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @NotNull
    private OffsetDateTime timestamp;
}
