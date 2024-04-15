package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void createUser(User user);

    User getUserById(Long id);

    void editUser(Long id, User user);

    void deleteUser(long id);

    User getUserByUsername(String username);
}