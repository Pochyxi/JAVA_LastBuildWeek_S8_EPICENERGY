package com.example.lastbuildweek.controllers;

import com.example.lastbuildweek.entities.Indirizzo;
import com.example.lastbuildweek.repositories.IndirizzoRepository;
import com.example.lastbuildweek.services.ComuneService;
import com.example.lastbuildweek.services.IndirizzoService;
import com.example.lastbuildweek.utils.IndirizzoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/indirizzi")
@Slf4j
@CrossOrigin(origins = "*")
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;

    @Autowired
    private ComuneService comuneService;

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Indirizzo>> get() throws Exception {

        return new ResponseEntity<>(
                indirizzoRepository.findAll(),
                HttpStatus.OK
        );
    }

    // RITORNA UN SINGOLO INDIRIZZO-LEGALE PER ID(PK)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Indirizzo> get( @PathVariable("id") Long id ) throws Exception {

        return new ResponseEntity<>(
                indirizzoService.getById( id ),
                HttpStatus.OK
        );
    }


    // AGGIUNGI UN NUOVO INDIRIZZO-LEGALE CON IL BODY COME RICHIESTA
    @PostMapping("/new-raw")
    @PreAuthorize("hasRole('ADMIN')")
    public Indirizzo create( @RequestBody IndirizzoRequest indirizzoRequest ) {

        try {


            return indirizzoService.createAndSave( indirizzoRequest );


        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

        return null;

    }


    //AGGIORNA LE PROPRIETA' DI UN INDIRIZZO-LEGALE
    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public Indirizzo update( @RequestBody IndirizzoRequest indirizzoRequest ) {

        try {

            return indirizzoService.createAndUpdate( indirizzoRequest );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }
        return Indirizzo.builder()
                .via( "Error on update indirizzo" )
                .build();
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById( @PathVariable("id") Long id ) {

        try {

            indirizzoService.delete( id );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

    }
}
