package br.com.connmandakaru.mandabank.service.user;

import br.com.connmandakaru.mandabank.dto.user.UserRequestDTO;
import br.com.connmandakaru.mandabank.dto.user.UserResponseDTO;
import br.com.connmandakaru.mandabank.entity.User;
import br.com.connmandakaru.mandabank.entity.enums.user.UserRole;
import br.com.connmandakaru.mandabank.mapper.UserMapper;
import br.com.connmandakaru.mandabank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponseDTO createUser(UserRequestDTO data) {
        User user = userMapper.toEntity(data);

        user.setPassword(passwordEncoder.encode(data.password()));

        if (data.cpf() != null && !data.cpf().isEmpty()) {
            user.setRole(UserRole.COMMON);
        } else {
            user.setRole(UserRole.MERCHANT);
        }

        user.setBalance(BigDecimal.ZERO);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }
}