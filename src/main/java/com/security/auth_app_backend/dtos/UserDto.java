package com.security.auth_app_backend.dtos;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.security.auth_app_backend.entities.Provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private UUID id;

    private String email;

    private String name;

    private String password;
    private String image;
    private boolean enable=true;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    private Provider provider=Provider.LOCAL;
    private Set<RoleDto> roles= new HashSet<>();
}
