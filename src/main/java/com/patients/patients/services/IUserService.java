package com.patients.patients.services;

import com.patients.patients.entities.User;

import java.util.List;

public interface IUserService {
    public User createUser(User user);
    public User findUserById(long id);
    public List<User> findAllUsers();
    public User updateUser(User newUser, long id);
    public void deleteUser(long id);
    public User findByEmail(String email);
}
