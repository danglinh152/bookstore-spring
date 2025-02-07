package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.service.UserService;
import com.danglinh.project_bookstore.util.annotation.ApiMessage;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ApiMessage("Fetch All Users")
    public ResponseEntity<ResponsePaginationDTO> getAllUsers(
            @Filter Specification<User> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(userService.findAllUsers(spec, pageable));
    }


    @GetMapping("/users/{id}")
    @ApiMessage("Fetch A User with Id")
    public ResponseEntity<User> getUser(@PathVariable int id) throws IdInvalidException {
        if (userService.findUserById(id) == null) {
            throw new IdInvalidException("User with id " + id + " not found");
        }
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping("/users")
    @ApiMessage("Create A User with Id")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (userService.addUser(user) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PutMapping("/users")
    @ApiMessage("Update A User")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (userService.updateUser(user) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete A User with Id")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("User with id " + id + " deleted successfully");
        }
        return ResponseEntity.internalServerError().build();
    }

}
