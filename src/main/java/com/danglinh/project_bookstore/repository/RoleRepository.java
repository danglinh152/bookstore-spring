package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    public Role findByRoleName(String roleName);

}
