package com.example.lastbuildweek.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    private String nomeCompleto;

    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
//    @JsonIgnore
    @ToString.Exclude
    List<Cliente> clienti = new ArrayList<>();

    public User( String nomeCompleto, String username, String password, String email ) {
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.username = username;
        this.password = password;
    }

    @ManyToMany // PIU UTENTI POSSONO AVERE PIU RUOLI E VICEVERSA
    @JoinTable(name = "user_roles", //Nome della tabbella che verrà creata
            joinColumns = @JoinColumn(name = "user_id"),// Crea colonna
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> roles = new HashSet<Role>();

    private Boolean active = true;

    public void addRole( Role r ) {

        this.roles.add( r );

    }

}
