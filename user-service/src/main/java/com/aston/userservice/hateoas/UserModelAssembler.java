package com.aston.userservice.hateoas;

import com.aston.userservice.controller.Controller;
import com.aston.userservice.dto.UserResponseDto;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserResponseDto, UserModel> {

    @Override
    public UserModel toModel(UserResponseDto entity) {

        UserModel model = new UserModel(entity);

        model.add(linkTo(methodOn(Controller.class).get(entity.id())).withSelfRel());
        model.add(linkTo(methodOn(Controller.class).getAll()).withRel("all-users"));
        model.add(linkTo(methodOn(Controller.class).update(entity.id(), null)).withRel("update"));
        model.add(linkTo(methodOn(Controller.class).delete(entity.id())).withRel("delete"));

        return model;
    }
}


