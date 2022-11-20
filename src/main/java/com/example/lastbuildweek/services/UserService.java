package com.example.lastbuildweek.services;

import com.example.lastbuildweek.entities.Role;
import com.example.lastbuildweek.entities.User;
import com.example.lastbuildweek.repositories.UserRepository;
import com.example.lastbuildweek.utils.RequestModels.UserRequest;
import com.example.lastbuildweek.utils.ResponseModels.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    public User save( User u ) {
        String psw = u.getPassword();
        u.setPassword( encoder.encode( psw ) );
        return userRepository.save( u );
    }

    public UserResponse createAndSave( UserRequest userRequest ) throws Exception {
        User u = new User();
        u.setNomeCompleto( userRequest.getNomeCompleto() );
        u.setEmail( userRequest.getEmail() );
        u.setUsername( userRequest.getUsername() );
        u.setPassword( encoder.encode( userRequest.getPassword() ) );

        Set<Role> roles = new HashSet<>();
        roles.add( roleService.getById( 1L ) );
        u.setRoles( roles );

        userRepository.save( u );
        return UserResponse.parseUser( u );
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById( Long id ) throws Exception {
        Optional<User> user = userRepository.findById( id );
        if( user.isEmpty() )
            throw new Exception( "User not available" );
        return user.get();
    }

    public void delete( Long id ) throws Exception {
        Optional<User> u = userRepository.findById( id );
        if( u.isPresent() ) {
            userRepository.delete( u.get() );
        } else {
            throw new Exception( "Utente non trovato" );
        }
    }

    public void update( User u ) {
        userRepository.save( u );
    }

    public UserResponse updateResponse( UserRequest userRequest, Long id ) {
        Optional<User> userFind = userRepository.findById( id );

        if( userFind.isPresent() ) {
            User u = new User();
            u.setId( userFind.get().getId() );
            u.setNomeCompleto( userRequest.getNomeCompleto() == null ? userFind.get().getNomeCompleto()
                    : userRequest.getNomeCompleto() );
            u.setEmail( userRequest.getEmail() == null ? userFind.get().getEmail() : userRequest.getEmail() );
            u.setUsername( userRequest.getUsername() == null ? userFind.get().getUsername() :
                    userRequest.getUsername() );
            u.setPassword( userFind.get().getPassword() );
            u.setRoles( userFind.get().getRoles() );
            u.setActive( userFind.get().getActive() );

            userRepository.save( u );
            return UserResponse.parseUser( userFind.get() );
        } else {
            return null;
        }


    }

    public Optional<User> findByUsername( String username ) throws Exception {

        return userRepository.findByUsername( username );
    }

    public Page<User> getAllPaginate( Pageable p ) {
        return userRepository.findAll( p );
    }
}