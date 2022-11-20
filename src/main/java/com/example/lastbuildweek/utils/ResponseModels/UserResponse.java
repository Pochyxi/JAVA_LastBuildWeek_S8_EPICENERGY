package com.example.lastbuildweek.utils.ResponseModels;

import com.example.lastbuildweek.entities.Role;
import com.example.lastbuildweek.entities.User;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserResponse {
    private Long id;
    private String nomeCompleto;
    private String email;
    private String username;
    private Set<Role> roles;

    public static UserResponse parseUser( User user ) {

        return UserResponse.builder()
                .id( user.getId() )
                .nomeCompleto( user.getNomeCompleto() )
                .email( user.getEmail() )
                .username( user.getUsername() )
                .roles( user.getRoles() )
                .build();
    }
}
