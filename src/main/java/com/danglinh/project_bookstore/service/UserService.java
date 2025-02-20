package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Role;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.RoleRepository;
import com.danglinh.project_bookstore.repository.UserRepository;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.danglinh.project_bookstore.util.error.UserCreationException;
import com.danglinh.project_bookstore.util.security.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, SecurityUtil securityUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User findUserById(int id) throws IdInvalidException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new IdInvalidException("User Not Found");
    }

    public ResponsePaginationDTO findAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> pageUser = userRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pageUser.getTotalElements());
        meta.setTotalPages(pageUser.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pageUser.getContent());

        return responsePaginationDTO;
    }


    public User addUser(User user) {
        List<String> errorMessages = new ArrayList<>();

        if (userRepository.existsByEmail(user.getEmail())) {
            errorMessages.add("Email already exists.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            errorMessages.add("Username already exists.");
        }

        Optional<Role> role = roleRepository.findById(user.getRole().getRoleId());
        if (role.isPresent()) {
            user.setRole(role.get());
        } else {
            user.setRole(null);
        }

        if (!errorMessages.isEmpty()) {
            throw new UserCreationException(errorMessages);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        Optional<User> currentUser = userRepository.findById(user.getUserId());
        if (currentUser.isPresent()) {
            User existingUser = currentUser.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            // Removed the password update line
            Optional<Role> role = roleRepository.findById(user.getRole().getRoleId());
            if (role.isPresent()) {
                existingUser.setRole(role.get());
            } else {
                existingUser.setRole(null);
            }
            existingUser.setActivate(user.getActivate());
            existingUser.setActivateCode(user.getActivateCode());
            existingUser.setRefreshToken(user.getRefreshToken());
            existingUser.setAvatar(user.getAvatar());
            existingUser.setBuyingAddress(user.getBuyingAddress());
            existingUser.setShippingAddress(user.getShippingAddress());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setGender(user.getGender());
            return userRepository.save(existingUser);
        }
        return null;
    }


    public Boolean deleteUser(int id) throws IdInvalidException {
        // Find the user by ID
        Optional<User> optionalUser = userRepository.findById(id);

        // Check if user exists
        if (optionalUser.isEmpty()) {
            return false; // User not found
        }

        User user = optionalUser.get();
        String currentUsername = SecurityUtil.getCurrentUser().orElse(null);

        // Check if the current user is trying to delete themselves
        if (currentUsername != null && currentUsername.equals(user.getUsername())) {
            throw new IdInvalidException("You can't delete yourself :)");
        }

        // Delete the user
        userRepository.delete(user);
        return true; // User deleted successfully
    }


    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updateRefreshToken(String username, String refreshToken) {
        User user = userRepository.findByUsername(username);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    public User findUserByUsernameAndRefreshToken(String username, String refreshToken) {
        return userRepository.findByUsernameAndRefreshToken(username, refreshToken);
    }

    public void updateActivateCode(String username, String activateCode) {
        User user = userRepository.findByUsername(username);
        user.setActivateCode(activateCode);
        userRepository.save(user);
    }

    public User findUserByUsernameAndActivateCode(String username, String activateCode) {
        return userRepository.findByUsernameAndActivateCode(username, activateCode);
    }

//    public Boolean activateAccount(String username, String activateCode) {
//        User user = userRepository.findByUsername(username);
//        if (user.getActivateCode().equals(activateCode)) {
//            user.setActivate(true);
//            userRepository.save(user);
//            return true;
//        } else {
//            user.setActivate(false);
//            userRepository.save(user);
//            return false;
//        }
//    }
}
