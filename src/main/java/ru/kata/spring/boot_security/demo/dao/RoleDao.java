package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleDao {
    Role getRoleByName(String roleName);
}