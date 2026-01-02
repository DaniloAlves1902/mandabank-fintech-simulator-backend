package br.com.connmandakaru.mandabank.repositories;

import br.com.connmandakaru.mandabank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
