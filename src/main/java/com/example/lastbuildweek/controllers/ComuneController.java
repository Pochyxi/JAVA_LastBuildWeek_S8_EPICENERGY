package com.example.lastbuildweek.controllers;


import com.example.lastbuildweek.entities.Comune;
import com.example.lastbuildweek.services.ComuneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/comuni")
@Slf4j
@CrossOrigin(origins = "*")
public class ComuneController {

    @Autowired
    private ComuneService comuneService;

    @PostMapping("/add-comune")
    @PreAuthorize("hasRole('ADMIN')")
    public String addComune() throws Exception {
        return comuneService.addComuni();
    }

    // RITORNA UN SINGOLO COMUNE PER ID(PK)
    @GetMapping("/{nome}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comune> getById( @PathVariable("nome") String nome ) throws Exception {

        return new ResponseEntity<>(
                comuneService.getByNome( nome ),
                HttpStatus.OK
        );
    }


    // AGGIUNGI UN NUOVO COMUNE CON IL BODY COME RICHIESTA
    @PostMapping("/new-raw")
    @PreAuthorize("hasRole('ADMIN')")
    public Comune create( @RequestBody Comune comune ) {

        try {

            comuneService.save( comune );

            return comune;

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

        return null;

    }




    //AGGIORNA LE PROPRIETA' DI UN COMUNE
    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public void update( @RequestBody Comune comune ) {

        try {

            comuneService.save( comune );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById( @PathVariable String id ) {

        try {

            comuneService.delete( id );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

    }
}
