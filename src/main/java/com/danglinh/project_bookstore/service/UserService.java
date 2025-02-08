package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.UserRepository;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.danglinh.project_bookstore.util.error.UserCreationException;
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

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        if (!errorMessages.isEmpty()) {
            throw new UserCreationException(errorMessages);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Boolean deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
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
