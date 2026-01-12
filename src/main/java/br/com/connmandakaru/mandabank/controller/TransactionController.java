package br.com.connmandakaru.mandabank.controller;

import br.com.connmandakaru.mandabank.dto.transaction.TransactionRequestDTO;
import br.com.connmandakaru.mandabank.dto.transaction.TransactionResponseDTO;
import br.com.connmandakaru.mandabank.service.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/pix")
    @PreAuthorize("hasRole('COMMON')")
    public ResponseEntity<TransactionResponseDTO> sendPIX(@RequestBody @Valid TransactionRequestDTO dto) {
        TransactionResponseDTO newTransaction = transactionService.sendPIX(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTransaction.id())
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
    }
}
