package br.com.connmandakaru.mandabank.service.transaction;

import br.com.connmandakaru.mandabank.dto.transaction.TransactionRequestDTO;
import br.com.connmandakaru.mandabank.dto.transaction.TransactionResponseDTO;
import br.com.connmandakaru.mandabank.entity.Transaction;
import br.com.connmandakaru.mandabank.entity.User;
import br.com.connmandakaru.mandabank.entity.enums.transactions.TransactionType;
import br.com.connmandakaru.mandabank.exception.user.UserNotFoundException;
import br.com.connmandakaru.mandabank.mapper.TransactionMapper;
import br.com.connmandakaru.mandabank.repositories.TransactionRepository;
import br.com.connmandakaru.mandabank.repositories.UserRepository;
import br.com.connmandakaru.mandabank.validation.transaction.ValidatePayerAndPayee;
import br.com.connmandakaru.mandabank.validation.transaction.ValidateTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionExecutionService executionService;
    private final TransactionFailureService failureService;
    private final TransactionMapper mapper;

    @Transactional
    public TransactionResponseDTO sendPIX(TransactionRequestDTO data) {

        User payer = userRepository.findById(data.payerId())
                .orElseThrow(() -> new UserNotFoundException("Payer not found"));

        User payee = userRepository.findById(data.payeeId())
                .orElseThrow(() -> new UserNotFoundException("Payee not found"));

        ValidatePayerAndPayee.validatePayerAndPayee(payer.getId(), payee.getId());
        ValidateTransaction.validate(payer, payee, data.transactionValue());

        Transaction transaction = mapper.toEntity(data);
        transaction.setPayer(payer);
        transaction.setPayee(payee);
        transaction.setTransactionType(TransactionType.PIX);
        transaction.markPending();

        transactionRepository.save(transaction);

        try {
            executionService.execute(payer, payee, data.transactionValue());

            transaction.complete();
            return mapper.toResponse(transaction);

        } catch (Exception ex) {

            failureService.markAsFailed(transaction.getId(), ex.getMessage());

            throw ex;
        }
    }
}