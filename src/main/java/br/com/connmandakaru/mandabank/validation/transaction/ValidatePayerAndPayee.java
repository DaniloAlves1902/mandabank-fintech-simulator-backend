package br.com.connmandakaru.mandabank.validation.transaction;

import br.com.connmandakaru.mandabank.exception.user.InvalidPayerException;

public class ValidatePayerAndPayee {

    public static void validatePayerAndPayee(String payerId, String payeeId) {

        if (payerId.equals(payeeId)) {
            throw new InvalidPayerException("Payer and Payee cannot be the same");
        }
    }

}
