package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleService {
    Role getRoleByName(String roleName);
}