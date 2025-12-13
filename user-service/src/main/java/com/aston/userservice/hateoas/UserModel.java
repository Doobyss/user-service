package com.aston.userservice.hateoas;

import com.aston.userservice.dto.UserResponseDto;
import org.springframework.hateoas.RepresentationModel;

public class UserModel extends RepresentationModel<UserModel> {

    private final UserResponseDto user;

    public UserModel(UserResponseDto user) {
        this.user = user;
    }

    public UserResponseDto getUser() {
        return user;
    }
}


