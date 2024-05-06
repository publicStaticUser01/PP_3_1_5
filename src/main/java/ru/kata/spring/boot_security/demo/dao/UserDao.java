package ru.kata.spring.boot_security.demo.dao;




import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    void createUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);

    void editUser(User user);

    void deleteUser(long id);
}