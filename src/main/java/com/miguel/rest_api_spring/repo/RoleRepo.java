package com.miguel.rest_api_spring.repo;

import com.miguel.rest_api_spring.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
