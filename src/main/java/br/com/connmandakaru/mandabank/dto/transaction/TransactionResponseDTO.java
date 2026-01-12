package br.com.connmandakaru.mandabank.dto.transaction;

import br.com.connmandakaru.mandabank.entity.Transaction;
import br.com.connmandakaru.mandabank.entity.enums.transactions.TransactionStatus;
import br.com.connmandakaru.mandabank.entity.enums.transactions.TransactionType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionResponseDTO(

        String id,
        String payerId,
        String payeeId,
        BigDecimal transactionValue,
        TransactionType transactionType,
        TransactionStatus transactionStatus,
        OffsetDateTime timestamp

) {

    public TransactionResponseDTO(Transaction transaction) {
        this(
                transaction.getId(), transaction.getPayer().getId(),
                transaction.getPayee() != null ? transaction.getPayee().getId() : null, transaction.getTransactionValue(),
                transaction.getTransactionType(), transaction.getTransactionStatus(), transaction.getTimestamp()
        );
    }
}
