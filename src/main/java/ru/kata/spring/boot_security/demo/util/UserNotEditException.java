package ru.kata.spring.boot_security.demo.util;

public class UserNotEditException extends RuntimeException {
    public UserNotEditException(String message) {
        super(message);
    }
}