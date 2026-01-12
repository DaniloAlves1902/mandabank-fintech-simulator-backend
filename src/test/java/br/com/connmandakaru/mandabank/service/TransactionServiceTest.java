package br.com.connmandakaru.mandabank.service;

import br.com.connmandakaru.mandabank.dto.transaction.TransactionRequestDTO;
import br.com.connmandakaru.mandabank.dto.transaction.TransactionResponseDTO;
import br.com.connmandakaru.mandabank.entity.Transaction;
import br.com.connmandakaru.mandabank.entity.User;
import br.com.connmandakaru.mandabank.entity.enums.transactions.TransactionStatus;
import br.com.connmandakaru.mandabank.entity.enums.transactions.TransactionType;
import br.com.connmandakaru.mandabank.entity.enums.user.UserRole;
import br.com.connmandakaru.mandabank.exception.transaction.InsufficientBalanceException;
import br.com.connmandakaru.mandabank.mapper.TransactionMapper;
import br.com.connmandakaru.mandabank.repositories.TransactionRepository;
import br.com.connmandakaru.mandabank.repositories.UserRepository;
import br.com.connmandakaru.mandabank.service.transaction.TransactionExecutionService;
import br.com.connmandakaru.mandabank.service.transaction.TransactionFailureService;
import br.com.connmandakaru.mandabank.service.transaction.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionExecutionService executionService;
    @Mock
    private TransactionFailureService failureService;
    @Mock
    private TransactionMapper mapper;

    @Test
    @DisplayName("You should successfully complete the PIX process when everything is ok.")
    void shouldSendPixSuccessfully() {

        User payer = new User();
        payer.setId("payer-id");
        payer.setBalance(new BigDecimal("200.00"));
        payer.setRole(UserRole.COMMON);

        User payee = new User();
        payee.setId("payee-id");

        TransactionRequestDTO request = new TransactionRequestDTO(
                "payer-id", "payee-id", new BigDecimal("100.00"), TransactionType.PIX
        );

        Transaction transactionEntity = new Transaction();
        transactionEntity.setId("trans-id");

        TransactionResponseDTO expectedResponse = new TransactionResponseDTO(
                "trans-id", "payer-id", "payee-id", new BigDecimal("100.00"),
                TransactionType.PIX, TransactionStatus.COMPLETED, OffsetDateTime.now()
        );

        when(userRepository.findById("payer-id")).thenReturn(Optional.of(payer));
        when(userRepository.findById("payee-id")).thenReturn(Optional.of(payee));
        when(mapper.toEntity(request)).thenReturn(transactionEntity);
        when(mapper.toResponse(transactionEntity)).thenReturn(expectedResponse);

        transactionService.sendPIX(request);


        verify(transactionRepository, times(1)).save(transactionEntity);

        verify(executionService, times(1)).execute(payer, payee, request.transactionValue());

    }

    @Test
    @DisplayName("It should throw an error if the balance is insufficient.")
    void shouldFailWhenBalanceIsInsufficient() {
        User payer = new User();
        payer.setId("payer-id");
        payer.setBalance(new BigDecimal("10.00"));
        payer.setRole(UserRole.COMMON);

        User payee = new User();
        payee.setId("payee-id");

        TransactionRequestDTO request = new TransactionRequestDTO(
                "payer-id", "payee-id", new BigDecimal("50.00"), TransactionType.PIX
        );

        when(userRepository.findById("payer-id")).thenReturn(Optional.of(payer));
        when(userRepository.findById("payee-id")).thenReturn(Optional.of(payee));

        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.sendPIX(request);
        });

        verify(transactionRepository, never()).save(any());
        verify(executionService, never()).execute(any(), any(), any());
    }
}