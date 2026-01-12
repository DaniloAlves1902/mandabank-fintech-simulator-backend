package br.com.connmandakaru.mandabank.dto.transaction;

import br.com.connmandakaru.mandabank.entity.enums.transactions.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequestDTO(

        @NotNull
        String payerId,

        String payeeId,

        @NotNull
        BigDecimal transactionValue,

        @NotNull
        TransactionType transactionType

) {
}
