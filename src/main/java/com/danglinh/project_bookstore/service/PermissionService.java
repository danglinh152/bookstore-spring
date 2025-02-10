package com.danglinh.project_bookstore.service;


import com.danglinh.project_bookstore.domain.DTO.response.Meta;
import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Permission;
import com.danglinh.project_bookstore.domain.entity.Permission;
import com.danglinh.project_bookstore.domain.entity.User;
import com.danglinh.project_bookstore.repository.PermissionRepository;
import com.danglinh.project_bookstore.repository.PermissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionService {
    private PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission findPermissionById(int id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        return permission.orElse(null);
    }

    public ResponsePaginationDTO findAllPermissions(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> pagePermission = permissionRepository.findAll(spec, pageable);
        Meta meta = new Meta();
        meta.setCurrentPage(pageable.getPageNumber() + 1); //luu y
        meta.setPageSize(pageable.getPageSize()); //luu y
        meta.setTotal(pagePermission.getTotalElements());
        meta.setTotalPages(pagePermission.getTotalPages());

        ResponsePaginationDTO responsePaginationDTO = new ResponsePaginationDTO();
        responsePaginationDTO.setMeta(meta);
        responsePaginationDTO.setData(pagePermission.getContent());

        return responsePaginationDTO;
    }

    public Permission addPermission(Permission permission) {
        return permissionRepository.save(permission);
    }


    public Permission updatePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Boolean deletePermission(int id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        if (permission.isPresent()) {
            permissionRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
