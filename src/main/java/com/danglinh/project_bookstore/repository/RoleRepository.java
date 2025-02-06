package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByRoleName(String roleName);

}
