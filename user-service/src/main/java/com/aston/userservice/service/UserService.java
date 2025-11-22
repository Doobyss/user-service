package com.aston.userservice.service;

import com.aston.userservice.dto.UserRequestDto;
import com.aston.userservice.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto create(UserRequestDto dto);
    UserResponseDto getById(Long id);
    List<UserResponseDto> getAll();
    UserResponseDto update(Long id, UserRequestDto dto);
    void delete(Long id);
}


