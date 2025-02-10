package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Permission;
import com.danglinh.project_bookstore.domain.entity.Role;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.PermissionRepository;
import com.danglinh.project_bookstore.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role findRoleById(int id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }

    public ResponsePaginationDTO findAllRoles(Specification<Role> spec, Pageable pageable) {
        Page<Role> pageRole = roleRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pageRole.getTotalElements());
        meta.setTotalPages(pageRole.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pageRole.getContent());

        return responsePaginationDTO;
    }

    public Role addRole(Role role) {
        if (role.getListOfPermissions() != null) {
            // Get the list of permission IDs
            List<Integer> listOfId = role.getListOfPermissions().stream()
                    .map(Permission::getPermissionId) // Use method reference for clarity
                    .collect(Collectors.toList()); // Collect to a List

            // Fetch permissions based on IDs
            List<Permission> permissions = permissionRepository.findAllById(listOfId);
            // Optionally set the permissions back to the role if needed
            role.setListOfPermissions(permissions);
        }
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
