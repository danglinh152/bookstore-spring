package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Permission;
import com.danglinh.project_bookstore.service.PermissionService;
import com.danglinh.project_bookstore.util.annotation.ApiMessage;
import com.danglinh.project_bookstore.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PermissionController {
    private PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/permissions")
    @ApiMessage("Fetch All Permissions")
    public ResponseEntity<ResponsePaginationDTO> getAllPermissions(
            @Filter Specification<Permission> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(permissionService.findAllPermissions(spec, pageable));
    }

    @GetMapping("/permissions/{id}")
    @ApiMessage("Fetch A Permission with Id")
    public ResponseEntity<Permission> getPermissionById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (permissionService.findPermissionById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(permissionService.findPermissionById(id));
    }

    @PostMapping("/permissions")
    @ApiMessage("Create A Permission")
    public ResponseEntity<Permission> createPermission(@Valid @RequestBody Permission permission) {
        if (permissionService.addPermission(permission) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(permission);
    }

    @PutMapping("/permissions")
    @ApiMessage("Update A Permission")
    public ResponseEntity<Permission> updatePermission(@Valid @RequestBody Permission permission) {
        if (permissionService.updatePermission(permission) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(permission);
    }

    @DeleteMapping("/permissions/{id}")
    @ApiMessage("Delete A Permission with Id")
    public ResponseEntity<String> deletePermission(@PathVariable int id) {
        if (permissionService.deletePermission(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Permission deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
