package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.entity.Role;
import com.danglinh.project_bookstore.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleById(int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        }
        return null;
    }

    public List<Role> findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            return null;
        }
        return roles;
    }

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    public Boolean deleteRole(int id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
