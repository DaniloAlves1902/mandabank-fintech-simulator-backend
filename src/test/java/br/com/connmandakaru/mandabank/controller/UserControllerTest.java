package br.com.connmandakaru.mandabank.controller;

import br.com.connmandakaru.mandabank.dto.user.UserRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should successfully register a COMMON user")
    void shouldRegisterCommonUserSuccessfully() throws Exception {

        UserRequestDTO userDTO = new UserRequestDTO(
                null,
                "Danilo",
                "Alves",
                "529.982.247-25",
                null,
                "danilo@test.com",
                "senhateste@"
        );

        mockMvc.perform(
                        post("/api/v1/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("danilo@test.com"))
                .andExpect(jsonPath("$.role").value("COMMON"));
    }

    @Test
    @DisplayName("Should successfully register a MERCHANT user")
    void shouldRegisterMerchantUserSuccessfully() throws Exception {

        UserRequestDTO userDTO = new UserRequestDTO(
                null,
                "Sarah",
                "Jennyfer",
                null,
                "04.252.011/0001-10",
                "sarah@test.com",
                "senhateste@"
        );

        mockMvc.perform(
                        post("/api/v1/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("sarah@test.com"))
                .andExpect(jsonPath("$.role").value("MERCHANT"));
    }

    @Test
    @DisplayName("It should fail when trying to register with invalid data.")
    void shouldFailToRegisterWithInvalidData() throws Exception {

        UserRequestDTO invalidUser = new UserRequestDTO(
                null,
                "",
                "",
                "123",
                null,
                "email",
                "123"
        );

        mockMvc.perform(
                        post("/api/v1/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidUser))
                )
                .andExpect(status().isBadRequest());
    }
}
