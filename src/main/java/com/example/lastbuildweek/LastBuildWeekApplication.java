package com.example.lastbuildweek;

import ch.qos.logback.classic.Logger;
import com.example.lastbuildweek.entities.Comune;
import com.example.lastbuildweek.entities.Role;
import com.example.lastbuildweek.entities.RoleType;
import com.example.lastbuildweek.entities.User;

import com.example.lastbuildweek.services.ComuneService;
import com.example.lastbuildweek.services.RoleService;
import com.example.lastbuildweek.services.UserService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.*;

@SpringBootApplication
public class LastBuildWeekApplication implements CommandLineRunner {

    private static final Logger logger
            = ( Logger ) LoggerFactory.getLogger( LastBuildWeekApplication.class);
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ComuneService comuneService;

    public static void main( String[] args ) {
        SpringApplication.run( LastBuildWeekApplication.class, args );
    }

    @Override
    public void run( String... args ) throws Exception {

        logger.info("CONTROLLO LA PRESENZA DEI RUOLI");
        List<Role> roles = roleService.getAll();

        if( roles.size() == 0 ) {
            logger.info( "RUOLI NON TROVATI, LI CREO" );

            Role roleUser = new Role();
            Role roleAdmin = new Role();
            roleUser.setRoleType( RoleType.ROLE_USER );
            roleAdmin.setRoleType( RoleType.ROLE_ADMIN );

            roleService.save(roleUser);
            roleService.save(roleAdmin);

            try {
                Comune comuneFinded = comuneService.getByNome( "Napoli" );
            } catch( Exception e ) {
                logger.info( "TEST DELLA PRESENZA DEL COMUNE NON PASSATO" );
                logger.info( "INIZIALIZZO COMUNI" );
                comuneService.addComuni();

                logger.info( "RIESEGUO TEST PRESENZA COMUNI" );
                try {
                    Comune comuneFinded = comuneService.getByNome( "Napoli" );
                    logger.info( "TEST DELLA PRESENZA DEL COMUNE PASSATO");

                } catch ( Exception ex ) {
                    logger.info( "TEST FALLITO ANCORA INSERIRE MANUALMENTE I COMUNI" );

                }
            }

            List<Role> rolesCheckAgain = roleService.getAll();

            checkAdmin( rolesCheckAgain );

        } else {
            logger.info("RUOLI TROVATI");
            checkAdmin( roles );
        }

    }

    public void checkAdmin(List<Role> roles) throws Exception {
        if( roles.size() > 0 ) {

            logger.info( "Eseguo controllo presenza account amministratore iniziale" );

            Optional<User> admin = userService.findByUsername( "admin" );

            if( admin.isPresent() ) {
                logger.info( "ACCOUNT ADMIN TROVATO: " );
                logger.info( "USERNAME: " + admin.get().getUsername() );
                logger.info( "Password: admin" );

                logger.warn( "Ricordarsi di contattare la rotta /auth/login per loggarsi con le credenziali" );

            } else {
                logger.error( "ACCOUNT ADMIN NON TROVATO" );
                logger.info( "INIZIALIZZO ACCOUNT ADMIN" );


                User newAdmin = new User();
                newAdmin.setNomeCompleto( "Admin" );
                newAdmin.setEmail( "admin@gmail.com" );
                newAdmin.setUsername( "admin" );
                newAdmin.setPassword( "admin" );

                Set<Role> rolesSet = new HashSet<>();
                rolesSet.add( roleService.getById( 1L ) );
                rolesSet.add( roleService.getById( 2L ) );
                newAdmin.setRoles( rolesSet );

                try {
                    userService.save( newAdmin );
                } catch( Exception e ) {
                    logger.error( e.getMessage() );
                }

                logger.info( "USERNAME: admin" );
                logger.info( "PASSWORD: admin" );
                logger.info( "Si consiglia in futuro di disattivare questo account e crearne uno personale" );
                logger.warn( "Ricordarsi di contattare la rotta /auth/login per loggarsi con le credenziali" );
            }
        }
    }
}
