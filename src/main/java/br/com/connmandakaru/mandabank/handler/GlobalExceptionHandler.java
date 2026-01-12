package br.com.connmandakaru.mandabank.handler;

import br.com.connmandakaru.mandabank.dto.error.ApiErrorDTO;
import br.com.connmandakaru.mandabank.exception.transaction.InsufficientBalanceException;
import br.com.connmandakaru.mandabank.exception.user.InvalidPayerException;
import br.com.connmandakaru.mandabank.exception.user.MerchantNotAuthorizedToMakeTransactionsException;
import br.com.connmandakaru.mandabank.exception.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleUserNotFound(UserNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiErrorDTO error = ApiErrorDTO.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error("Resource Not Found")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiErrorDTO> handleInsufficientBalance(InsufficientBalanceException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        ApiErrorDTO error = ApiErrorDTO.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error("Insufficient Balance")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MerchantNotAuthorizedToMakeTransactionsException.class)
    public ResponseEntity<ApiErrorDTO> handleMerchantNotAuthorized(
            MerchantNotAuthorizedToMakeTransactionsException e,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        ApiErrorDTO error = ApiErrorDTO.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error("Forbidden")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidPayerException.class)
    public ResponseEntity<ApiErrorDTO> handleInvalidPayer(
            InvalidPayerException e,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ApiErrorDTO error = ApiErrorDTO.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error("Invalid Payer")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiErrorDTO error = ApiErrorDTO.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error("Invalid Argument")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorDTO> handleBadCredentials(BadCredentialsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ApiErrorDTO error = ApiErrorDTO.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error("Authentication Failed")
                .message("Invalid credentials.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGenericException(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error("An unexpected error occurred at path: {}", request.getRequestURI(), e);

        ApiErrorDTO error = ApiErrorDTO.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error("Internal Server Error")
                .message("An unexpected internal server error has occurred.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }
}
