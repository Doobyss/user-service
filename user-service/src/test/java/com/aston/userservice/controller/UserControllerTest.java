package com.aston.userservice.controller;

import com.aston.userservice.dto.UserRequestDto;
import com.aston.userservice.dto.UserResponseDto;
import com.aston.userservice.hateoas.UserModel;
import com.aston.userservice.hateoas.UserModelAssembler;
import com.aston.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = Controller.class,
        properties = "spring.config.import="
)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @MockBean
    private UserModelAssembler assembler;

    @Test
    void testCreateUser() throws Exception {

        UserResponseDto responseDto =
                new UserResponseDto(1L, "Alex", "alex@mail.com", 30);

        UserModel userModel = new UserModel(responseDto);

        Mockito.when(service.create(Mockito.any()))
                .thenReturn(responseDto);

        Mockito.when(assembler.toModel(Mockito.any()))
                .thenReturn(userModel);

        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
        {"name":"Alex","email":"alex@mail.com","age":30}
        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.name").value("Alex"))
                .andExpect(jsonPath("$.user.email").value("alex@mail.com"));
    }
}

