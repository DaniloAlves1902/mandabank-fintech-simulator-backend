package br.com.connmandakaru.mandabank.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CpfOrCnpjValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCpfOrCnpj {
    String message() default "Invalid CPF/CNPJ information. / Informações de CPF/CNPJ inválidas.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
