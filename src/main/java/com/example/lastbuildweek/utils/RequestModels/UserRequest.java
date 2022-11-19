package com.example.lastbuildweek.utils.RequestModels;

import com.example.lastbuildweek.entities.User;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRequest {
    private String nomeCompleto;
    private String email;
    private String username;
    private String password;

    public static UserRequest parseUser( User user) {
        return UserRequest.builder()
               .nomeCompleto(user.getNomeCompleto())
               .email(user.getEmail())
               .username(user.getUsername())
               .password(user.getPassword())
               .build();
    }
}
