package com.aston.userservice.controller;

import com.aston.userservice.dto.UserRequestDto;
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

@WebMvcTest(Controller.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    void testCreateUser() throws Exception {

        Mockito.when(service.create(Mockito.any()))
                .thenReturn(new com.aston.userservice.dto.UserResponseDto(
                        1L, "Alex", "alex@mail.com", 30
                ));

        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"name":"Alex","email":"alex@mail.com","age":30}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
