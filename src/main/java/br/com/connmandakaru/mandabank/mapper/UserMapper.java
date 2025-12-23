package br.com.connmandakaru.mandabank.mapper;

import br.com.connmandakaru.mandabank.dto.user.UserRequestDTO;
import br.com.connmandakaru.mandabank.dto.user.UserResponseDTO;
import br.com.connmandakaru.mandabank.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponse(User entity);
}