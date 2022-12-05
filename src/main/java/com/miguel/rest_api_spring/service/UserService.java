package com.miguel.rest_api_spring.service;


import com.miguel.rest_api_spring.domain.AppUser;
import com.miguel.rest_api_spring.domain.Role;

import java.util.List;


public interface UserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
