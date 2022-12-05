package com.miguel.rest_api_spring.repo;

import com.miguel.rest_api_spring.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
