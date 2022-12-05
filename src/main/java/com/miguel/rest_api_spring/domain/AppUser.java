package com.miguel.rest_api_spring.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

import static jakarta.persistence.GenerationType.AUTO;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {

    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String username;
    private String name;
    private String password;

    // MANY USERS TO MANY ROLES IN DB
    // FETCH TYPE SET TO EAGER, SO THAT WHEN LOADING UP USERS, NEED TO LOAD UP ROLES AS WELL
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

}
