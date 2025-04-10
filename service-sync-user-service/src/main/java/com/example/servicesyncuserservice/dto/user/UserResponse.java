package com.example.servicesyncuserservice.dto.user;

import com.example.servicesyncuserservice.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String username;

    private String name;

    private String surname;

    private Set<RoleType> roles;

}
