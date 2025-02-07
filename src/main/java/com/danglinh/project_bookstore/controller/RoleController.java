package com.danglinh.project_bookstore.controller;


import com.danglinh.project_bookstore.domain.DTO.response.ResponsePaginationDTO;
import com.danglinh.project_bookstore.domain.entity.Book;
import com.danglinh.project_bookstore.domain.entity.Role;
import com.danglinh.project_bookstore.service.RoleService;
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
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    @ApiMessage("Fetch All Roles")
    public ResponseEntity<ResponsePaginationDTO> getAllRoles(
            @Filter Specification<Role> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(roleService.findAllRoles(spec, pageable));
    }

    @GetMapping("/roles/{id}")
    @ApiMessage("Fetch A Role with Id")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) throws IdInvalidException {
        if (id > 9999) {
            throw new IdInvalidException("Id more than 9999");
        }
        if (roleService.findRoleById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roleService.findRoleById(id));
    }

    @PostMapping("/roles")
    @ApiMessage("Create A Role")
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) {
        if (roleService.addRole(role) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @PutMapping("/roles")
    @ApiMessage("Update A Role")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody Role role) {
        if (roleService.updateRole(role) == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @DeleteMapping("/roles/{id}")
    @ApiMessage("Delete A Role with Id")
    public ResponseEntity<String> deleteRole(@PathVariable int id) {
        if (roleService.deleteRole(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Role deleted");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
