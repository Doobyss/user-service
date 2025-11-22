package com.aston.userservice.service;

import com.aston.userservice.dto.UserRequestDto;
import com.aston.userservice.dto.UserResponseDto;
import com.aston.userservice.entity.User;
import com.aston.userservice.exception.UserNotFoundException;
import com.aston.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserResponseDto create(UserRequestDto dto) {
        User user = new User(null, dto.name(), dto.email(), dto.age());
        User saved = repository.save(user);
        return toDto(saved);
    }

    @Override
    public UserResponseDto getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return toDto(user);
    }

    @Override
    public List<UserResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setAge(dto.age());

        return toDto(repository.save(user));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }
}



