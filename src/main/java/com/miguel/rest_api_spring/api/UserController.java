package com.miguel.rest_api_spring.api;
import com.miguel.rest_api_spring.domain.AppUser;
import com.miguel.rest_api_spring.domain.Role;
import com.miguel.rest_api_spring.service.UserServiceImplementation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api")
public class UserController {

    // INJECTED BY LOMBOK
    private final UserServiceImplementation userService;

    // GET ALL USERS
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    // GET USER

    // SAVE USER
    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(
            @RequestBody AppUser user
    ) {
        // URI
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    // SAVE ROLE
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(
            @RequestBody Role role
    ) {
        // URI
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());

        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    // ADD ROLE TO USER
    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(
        @RequestBody RoleToUserForm form
    ) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}

// Object to return from add role to user method
@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
