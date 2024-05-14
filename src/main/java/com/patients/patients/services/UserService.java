package com.patients.patients.services;

import com.patients.patients.entities.User;
import com.patients.patients.exceptions.UserNotFoundException;
import com.patients.patients.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class UserService implements IUserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public User createUser(User user) {
        if(user.getPassword().length() < 8){
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User newUser, long id) {
        return userRepository.findById(id)
                .map(post -> {
                    if (newUser.getName() != null) {
                        post.setName(newUser.getName());
                    }
                    if (newUser.getEmail() != null) {
                        post.setEmail(newUser.getEmail());
                    }
                    if (newUser.getPassword() != null) {
                        post.setPassword(newUser.getPassword());
                    }
                    if (newUser.getRole() != null) {
                        post.setRole(newUser.getRole());
                    }
                    return userRepository.save(post);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public void deleteUser(long id) {

    }


}
