package br.com.connmandakaru.mandabank.validation.transaction;

import br.com.connmandakaru.mandabank.entity.User;
import br.com.connmandakaru.mandabank.entity.enums.user.UserRole;
import br.com.connmandakaru.mandabank.exception.transaction.InsufficientBalanceException;
import br.com.connmandakaru.mandabank.exception.user.MerchantNotAuthorizedToMakeTransactionsException;
import br.com.connmandakaru.mandabank.exception.user.UserNotFoundException;

import java.math.BigDecimal;

public class ValidateTransaction {

    /**
     * <p><strong>TODO:</strong> Refactor transaction validations into a scalable rule-based model.</p>
     *
     * <ul>
     *   <li>Create a {@code TransactionRule} interface to represent independent business rules</li>
     *   <li>Implement each validation (balance, user role, amount, user existence) in separate classes</li>
     *   <li>Create a {@code TransactionValidator} as a rule orchestrator
     *       (Chain of Responsibility pattern)</li>
     *   <li>Allow easy addition, removal, or modification of rules without impacting the main flow</li>
     * </ul>
     *
     * <p><strong>PT-BR:</strong></p>
     *
     * <p><strong>TODO:</strong> Refatorar as validações de transação para um modelo escalável baseado em regras.</p>
     *
     * <ul>
     *   <li>Criar a interface {@code TransactionRule} para representar regras de negócio independentes</li>
     *   <li>Implementar cada validação em classes separadas</li>
     *   <li>Criar um {@code TransactionValidator} como orquestrador das regras
     *       (Chain of Responsibility)</li>
     *   <li>Permitir fácil evolução das regras sem impactar o fluxo principal</li>
     * </ul>
     */


    public static void validate(User payer, User payee, BigDecimal amount) {

        if (payer == null || payee == null) {
            throw new UserNotFoundException("Payer or payee not found");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero");
        }

        if (payer.getRole() == UserRole.MERCHANT) {
            throw new MerchantNotAuthorizedToMakeTransactionsException(
                    "Merchant not authorized to conduct transactions"
            );
        }

        if (payer.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for transaction");
        }
    }
}
