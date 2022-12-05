package com.miguel.rest_api_spring.service;

import com.miguel.rest_api_spring.domain.AppUser;
import com.miguel.rest_api_spring.domain.Role;
import com.miguel.rest_api_spring.repo.RoleRepo;
import com.miguel.rest_api_spring.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImplementation implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user {} to the database", user.getName());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        log.info("Adding role {} to user {} ", roleName, username);

        // GET USER
        AppUser user = userRepo.findByUsername(username);
        // GET ROLE
        Role role = roleRepo.findByName(roleName);

        if(
                user != null &&
                        role != null
        ) {
            user.getRoles().add(role);
        }
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {} from the database", username);

        return userRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }
}
