package com.aston.userservice.controller;

import com.aston.userservice.dto.UserRequestDto;
import com.aston.userservice.dto.UserResponseDto;
import com.aston.userservice.hateoas.UserModel;
import com.aston.userservice.hateoas.UserModelAssembler;
import com.aston.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class Controller {

    private final UserService service;
    private final UserModelAssembler assembler;

    @Operation(summary = "Создать пользователя")
    @PostMapping
    public UserModel create(@RequestBody UserRequestDto dto) {
        UserResponseDto user = service.create(dto);
        return assembler.toModel(user);
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public UserModel get(@PathVariable Long id) {
        UserResponseDto user = service.getById(id);
        return assembler.toModel(user);
    }

    @Operation(summary = "Получить список всех пользователей")
    @GetMapping
    public CollectionModel<UserModel> getAll() {
        List<UserResponseDto> users = service.getAll();
        return assembler.toCollectionModel(users);
    }

    @Operation(summary = "Обновить пользователя")
    @PutMapping("/{id}")
    public UserModel update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        UserResponseDto user = service.update(id, dto);
        return assembler.toModel(user);
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
