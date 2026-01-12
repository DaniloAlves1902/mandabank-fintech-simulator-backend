package br.com.connmandakaru.mandabank.configuration.doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "MandaBank API",
                version = "v1",
                description = "API for managing users, transactions, and authentication. "
                        + "This API simulates a fintech application focused on PIX transfers and can be expanded to support TED and other financial operations. "
                        + "/ API para gerenciamento de usuários, transações e autenticação. "
                        + "Esta API simula uma aplicação fintech com foco em transferências via PIX e pode ser expandida para suportar TED e outras operações financeiras."

        )
)
public class OpenApiConfig {
}
