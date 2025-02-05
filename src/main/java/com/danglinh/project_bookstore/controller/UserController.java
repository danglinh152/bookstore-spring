package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.service.UserService;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> listOfUsers = userService.findAllUsers();
        if (listOfUsers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfUsers);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) throws IdInvalidException {
        if (userService.findUserById(id) == null) {
            throw new IdInvalidException("User with id " + id + " not found");
        }
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (userService.addUser(user) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (userService.updateUser(user) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("User with id " + id + " deleted successfully");
        }
        return ResponseEntity.internalServerError().build();
    }

}
