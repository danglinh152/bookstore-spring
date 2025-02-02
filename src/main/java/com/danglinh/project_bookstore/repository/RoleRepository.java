package com.danglinh.project_bookstore.repository;

import com.danglinh.project_bookstore.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "roles")
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByRoleName(String roleName);

}
