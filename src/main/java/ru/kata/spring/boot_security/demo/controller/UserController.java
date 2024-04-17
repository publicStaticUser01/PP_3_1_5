package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;



@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/user")
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails ud = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userService.getUserByUsername(ud.getUsername()));
        return "user";
    }

    @GetMapping("/user/{id}")
    public String userPage(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

}