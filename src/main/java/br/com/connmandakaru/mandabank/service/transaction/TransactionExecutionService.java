package br.com.connmandakaru.mandabank.service.transaction;

import br.com.connmandakaru.mandabank.entity.User;
import br.com.connmandakaru.mandabank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionExecutionService {

    private final UserRepository userRepository;

    @Transactional
    public void execute(User payer, User payee, BigDecimal value) {

        payer.setBalance(payer.getBalance().subtract(value));

        payee.setBalance(payee.getBalance().add(value));

        userRepository.save(payer);
        userRepository.save(payee);
    }
}
