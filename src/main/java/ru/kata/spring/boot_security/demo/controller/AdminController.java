package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class AdminController {

    private static final String REDIRECT_ADMIN = "redirect:/admin";
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }
    @GetMapping("/create")
    public String creator(Model model) {
        model.addAttribute("user", new User());
        return "creator";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(name = "ROLE_USER", defaultValue = "false") boolean userRole,
                         @RequestParam(name = "ROLE_ADMIN", defaultValue = "false") boolean adminRole) {
        if (bindingResult.hasErrors()) {
            return "creator";
        }

        if (userRole) {
            user.addRole(roleService.getRoleByName("ROLE_USER"));
        }
        if (adminRole) {
            user.addRole(roleService.getRoleByName("ROLE_ADMIN"));
        }
        userService.createUser(user);
        return REDIRECT_ADMIN;
    }

    @GetMapping("/edit")
    public String editor(@RequestParam(value = "id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        for (Role role : userService.getUserById(id).getRoles()) {
            if ("ROLE_USER".equals(role.getName())) {
                model.addAttribute("userRole", true);
            }
            if ("ROLE_ADMIN".equals(role.getName())) {
                model.addAttribute("adminRole", true);
            }
        }
        return "editor";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("user") @Valid User user,
                       BindingResult bindingResult,
                       @RequestParam(value = "id") long id,
                       @RequestParam(name = "ROLE_USER", defaultValue = "false") boolean userRole,
                       @RequestParam(name = "ROLE_ADMIN", defaultValue = "false") boolean adminRole) {
        if (bindingResult.hasErrors()) {
            return "editor";
        }

        if (userRole) {
            user.addRole(roleService.getRoleByName("ROLE_USER"));
        }
        if (adminRole) {
            user.addRole(roleService.getRoleByName("ROLE_ADMIN"));
        }
        userService.editUser(id, user);
        return REDIRECT_ADMIN;
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return REDIRECT_ADMIN;
    }
}