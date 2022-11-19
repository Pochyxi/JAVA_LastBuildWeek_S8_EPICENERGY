package com.example.lastbuildweek.controllers;


import com.example.lastbuildweek.entities.RoleType;
import com.example.lastbuildweek.entities.User;
import com.example.lastbuildweek.services.RoleService;
import com.example.lastbuildweek.services.UserService;
import com.example.lastbuildweek.utils.RequestModels.UserRequest;
import com.example.lastbuildweek.utils.ResponseModels.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    //RITORNA TUTTI GLI UTENTI
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @CrossOrigin
    public List<User> getAllUsers() {

        return userService.getAll();

    }

    //RITORNA TUTTI GLI UTENTI CON POSSIBILITA' DI PAGINAZIONE
    @GetMapping("/pageable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<User>> getAllUsersPageable( Pageable p ) {

        Page<User> findAll = userService.getAllPaginate( p );

        if( findAll.hasContent() ) {
            return new ResponseEntity<>( findAll, HttpStatus.OK );
        } else {
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND );
        }

    }

    // RITORNA UN SINGOLO USER PER ID(PK)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getById( @PathVariable Long id ) throws Exception {

        return new ResponseEntity<>(
                userService.getById( id ),
                HttpStatus.OK
        );
    }

    //RITORNA TUTTI GLI UTENTI PER USERNAME(PK)
    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getByUsername( @PathVariable String username ) throws Exception {

        return new ResponseEntity<>(
                userService.findByUsername( username ).isPresent() ?
                        userService.findByUsername( username ).get() : null,
                HttpStatus.OK
        );

    }

    // AGGIUNGI UN NUOVO UTENTE CON IL BODY COME RICHIESTA
    @PostMapping("/new-raw")
//    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse create( @RequestBody UserRequest user ) {

        try {

            return userService.createAndSave( user );


        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

        return null;

    }


    //AGGIORNA LE PROPRIETA' DI UN UTENTE
    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public void update( @RequestBody User user ) {

        try {

            userService.save( user );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }
    }

    // AGGIUNGI UN NUOVO RUOLO ALL'UTENTE
    @PutMapping("/{id}/add-role/{roleType}")
    @PreAuthorize("hasRole('ADMIN')")
    public void addRole(
            @PathVariable("id") Long id,
            @PathVariable("roleType") String roleType
    ) throws Exception {

        User u = userService.getById( id );

        if( roleType.equals( "ADMIN" ) ) {

            u.addRole( roleService.getByRole( RoleType.ROLE_ADMIN ) );

            userService.update( u );

        }

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById( @PathVariable Long id ) {

        try {

            userService.delete( id );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

    }
}
