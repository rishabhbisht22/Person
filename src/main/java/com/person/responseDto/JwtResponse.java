package com.person.responseDto;

import com.person.entity.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {

    String token;
    String type = "Bearer";
    Integer id;
    String name;
    String email;
    Set<Role> roles;

    public JwtResponse(Integer id, String name, String email, Set<Role> roles, String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.token = token;
    }
}
