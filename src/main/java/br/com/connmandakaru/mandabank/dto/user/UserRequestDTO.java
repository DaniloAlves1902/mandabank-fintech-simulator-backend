package br.com.connmandakaru.mandabank.dto.user;

import br.com.connmandakaru.mandabank.validation.CpfOrCnpjRequired;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@CpfOrCnpjRequired
public record UserRequestDTO(
        UUID id,

        @NotBlank(message = "First name cannot be empty")
        String firstName,

        @NotBlank(message = "Last name cannot be empty")
        String lastName,

        @NotBlank(message = "CPF is required")
        @CPF(message = "Invalid CPF format")
        String cpf,

        @NotBlank(message = "CNPJ is required")
        @CNPJ(message = "Invalid CNPJ format")
        String cnpj,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password
) {
}