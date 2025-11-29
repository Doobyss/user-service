package com.aston.userservice.controller;

import com.aston.userservice.dto.UserRequestDto;
import com.aston.userservice.dto.UserResponseDto;
import com.aston.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class Controller {

    private final UserService service;

    @PostMapping
    public UserResponseDto create(@RequestBody UserRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDto get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id,
                                  @RequestBody UserRequestDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
