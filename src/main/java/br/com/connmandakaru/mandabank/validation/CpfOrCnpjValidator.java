package br.com.connmandakaru.mandabank.validation;

import br.com.connmandakaru.mandabank.dto.user.UserRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfOrCnpjValidator implements ConstraintValidator<ValidCpfOrCnpj, UserRequestDTO> {

    @Override
    public boolean isValid(UserRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null) return false;

        boolean haveCpf = dto.cpf() != null && !dto.cpf().isBlank();
        boolean haveCnpj = dto.cnpj() != null && !dto.cnpj().isBlank();

        if (!haveCpf && !haveCnpj) {
            buildViolation(context, "CPF or CNPJ must be provided");
            return false;
        }

        if (haveCpf && haveCnpj) {
            buildViolation(context, "CPF and CNPJ cannot be provided at the same time");
            return false;
        }

        return true;
    }

    private void buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
