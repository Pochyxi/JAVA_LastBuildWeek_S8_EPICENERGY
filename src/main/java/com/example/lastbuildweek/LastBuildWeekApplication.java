package com.example.lastbuildweek;

import ch.qos.logback.classic.Logger;
import com.example.lastbuildweek.entities.Role;
import com.example.lastbuildweek.entities.RoleType;
import com.example.lastbuildweek.entities.User;
import com.example.lastbuildweek.repositories.UserRepository;
import com.example.lastbuildweek.services.RoleService;
import com.example.lastbuildweek.services.UserService;
import com.example.lastbuildweek.utils.CSVReader;
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

    public static void main( String[] args ) {
        SpringApplication.run( LastBuildWeekApplication.class, args );
    }

    @Override
    public void run( String... args ) throws Exception {

        logger.info("CONTROLLO LA PRESENZA DEI RUOLI");
        List<Role> roles = roleService.getAll();

        if( roles.size() == 0 ) {
            logger.info( "RUOLI NON TROVATI, LI CREO" );
            logger.info( "RIAVVIA IL PROGRAMMA PER CREARE L'ACCOUNT ADMIN DI DEFAULT" );

            Role roleUser = new Role();
            Role roleAdmin = new Role();
            roleUser.setRoleType( RoleType.ROLE_USER );
            roleAdmin.setRoleType( RoleType.ROLE_ADMIN );

            roleService.save(roleUser);
            roleService.save(roleAdmin);

        } else {
            logger.info("RUOLI TROVATI");
        }

        if( roles.size() > 0 ) {

            logger.info( "Eseguo controllo presenza account amministratore iniziale" );

            Optional<User> admin = userService.findByUsername( "admin" );

            if( admin.isPresent() ) {
                logger.info( "ACCOUNT ADMIN TROVATO: " );
                logger.info( "USERNAME: " + admin.get().getUsername() );
                logger.info( "Password: admin" );

                logger.warn( "Ricordarsi di contattare la rotta /auth/login per loggarsi con le credenziali" );
                logger.warn( "Ricordarsi inoltre di contattare la rotta /api/comuni/add-comune (solo la prima volta) " +
                        "per inizializzare comuni e province" );
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
            }
        }

    }
}
