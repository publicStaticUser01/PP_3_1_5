package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    public static final String REDIRECT_ADMIN = "redirect:/admin";

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String adminPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails ud = (UserDetails) authentication.getPrincipal();

        Set<Role> allRoles = new HashSet<>();
        allRoles.add(new Role("ROLE_USER"));
        allRoles.add(new Role("ROLE_ADMIN"));

        model.addAttribute("person", new User());
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("currentUser", userService.getUserByUsername(ud.getUsername()));
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("person") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam("selectedRoles") List<String> selectedRoles) {
        if (bindingResult.hasErrors()) {
            System.out.println("Incorrect create input");
            return REDIRECT_ADMIN;
        }

        for (String roleName : selectedRoles) {
            user.addRole(roleService.getRoleByName(roleName));
        }
        userService.createUser(user);
        return REDIRECT_ADMIN;
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("id") long id,
                       @RequestParam("edit_name") @Valid String name,
                       @RequestParam("edit_lastName") String lastName,
                       @RequestParam("edit_age") Integer age,
                       @RequestParam("edit_username") String username,
                       @RequestParam("edit_password") String password,
                       @RequestParam("selectedRoles") List<String> selectedRoles) {
        User user = new User(name, lastName, age, username, password);

        for (String roleName : selectedRoles) {
            user.addRole(roleService.getRoleByName(roleName));
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