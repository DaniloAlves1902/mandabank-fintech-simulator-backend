package br.com.connmandakaru.mandabank.exception.user;

public class MerchantNotAuthorizedToMakeTransactionsException extends RuntimeException {
    public MerchantNotAuthorizedToMakeTransactionsException(String message) {
        super(message);
    }
}
