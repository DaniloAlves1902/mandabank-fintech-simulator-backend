package br.com.connmandakaru.mandabank.repositories;

import br.com.connmandakaru.mandabank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByEmail(String email);

    User findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String email);
}
