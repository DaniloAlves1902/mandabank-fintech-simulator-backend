package br.com.connmandakaru.mandabank.service.transaction;

import br.com.connmandakaru.mandabank.entity.Transaction;
import br.com.connmandakaru.mandabank.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionFailureService {

    private final TransactionRepository transactionRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsFailed(String transactionId, String reason) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow();

        transaction.fail(reason);
        transactionRepository.save(transaction);
    }
}
