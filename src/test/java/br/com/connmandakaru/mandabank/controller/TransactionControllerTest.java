package br.com.connmandakaru.mandabank.controller;

import br.com.connmandakaru.mandabank.dto.transaction.TransactionRequestDTO;
import br.com.connmandakaru.mandabank.dto.transaction.TransactionResponseDTO;
import br.com.connmandakaru.mandabank.entity.enums.transactions.TransactionStatus;
import br.com.connmandakaru.mandabank.entity.enums.transactions.TransactionType;
import br.com.connmandakaru.mandabank.service.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        this.mockMvc = webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("COMMON user should successfully complete a PIX transaction (HTTP 201)")
    @WithMockUser(roles = "COMMON")
    void shouldPerformPixTransactionSuccessfully() throws Exception {

        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                "payer-id",
                "payee-id",
                new BigDecimal("100.00"),
                TransactionType.PIX
        );

        TransactionResponseDTO responseDTO = new TransactionResponseDTO(
                "transaction-id",
                "payer-id",
                "payee-id",
                new BigDecimal("100.00"),
                TransactionType.PIX,
                TransactionStatus.COMPLETED,
                OffsetDateTime.now()
        );

        when(transactionService.sendPIX(any(TransactionRequestDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(
                        post("/api/v1/transactions/pix")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.transactionValue").value(100.00));
    }

    @Test
    @DisplayName("MERCHANT user should NOT be allowed to perform a PIX transaction (HTTP 403)")
    @WithMockUser(roles = "MERCHANT")
    void shouldNotAllowPixTransactionForMerchantUser() throws Exception {

        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                "payer-id",
                "payee-id",
                new BigDecimal("100.00"),
                TransactionType.PIX
        );

        mockMvc.perform(
                        post("/api/v1/transactions/pix")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestDTO))
                )
                .andExpect(status().isForbidden());

        verify(transactionService, never()).sendPIX(any());
    }


}
