package br.com.connmandakaru.mandabank.mapper;

import br.com.connmandakaru.mandabank.dto.transaction.TransactionRequestDTO;
import br.com.connmandakaru.mandabank.dto.transaction.TransactionResponseDTO;
import br.com.connmandakaru.mandabank.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "payer", ignore = true)
    @Mapping(target = "payee", ignore = true)
    @Mapping(target = "transactionStatus", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    Transaction toEntity(TransactionRequestDTO dto);

    TransactionResponseDTO toResponse(Transaction entity);
}
