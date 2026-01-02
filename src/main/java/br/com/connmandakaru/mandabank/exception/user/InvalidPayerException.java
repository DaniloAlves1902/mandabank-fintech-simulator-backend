package br.com.connmandakaru.mandabank.exception.user;

public class InvalidPayerException extends RuntimeException {
  public InvalidPayerException(String message) {
    super(message);
  }
}
