package br.com.connmandakaru.mandabank.controller;

import br.com.connmandakaru.mandabank.dto.user.UserRequestDTO;
import br.com.connmandakaru.mandabank.dto.user.UserResponseDTO;
import br.com.connmandakaru.mandabank.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRequestDTO dto) {
        UserResponseDTO newClient = userService.createUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }
}
