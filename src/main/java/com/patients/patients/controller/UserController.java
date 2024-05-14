package com.patients.patients.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.patients.patients.DTO.UserDTO;
import com.patients.patients.DTO.UserBriefDTO;
import com.patients.patients.entities.Post;
import com.patients.patients.entities.User;
import com.patients.patients.services.UserService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

    private final UserService service;

    private final ModelMapper modelMapper;
    private final AuthController authController;

    UserController(UserService service, ModelMapper modelMapper,AuthController authController) {
        this.service = service;
        this.modelMapper = modelMapper;
        this.authController = authController;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<UserBriefDTO> all() {
        return service.findAllUsers().stream()
                .map(this::convertToBriefDTO)
                .collect(Collectors.toList());
    }
    // end::get-aggregate-root[]

    @PostMapping("/register")
    User newUser(@RequestBody User newUser) {
        return service.createUser(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")
    UserDTO one(@PathVariable Long id) {

        try {
            return convertToDetailsDTO(service.findUserById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/users/{id}")
    UserDTO replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return convertToDetailsDTO(service.updateUser(newUser,id));
    }
    @PutMapping("/userData")
    UserDTO replaceCurrentUser(@RequestBody User newUser) throws Exception {
        Long userId = authController.getCurrentUserId();

        if (userId== 0) {
            throw new Exception("User not found");
        }
        return convertToDetailsDTO(service.updateUser(newUser,userId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    private UserBriefDTO convertToBriefDTO(User user) {
        UserBriefDTO userBriefDTO = modelMapper.map(user, UserBriefDTO.class);
        return userBriefDTO;
    }
    private UserDTO convertToDetailsDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
