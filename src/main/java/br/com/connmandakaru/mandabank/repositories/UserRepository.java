package br.com.connmandakaru.mandabank.repositories;

import br.com.connmandakaru.mandabank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<String> findByEmail (String email);
    boolean existsByEmail (String email);
}
