package com.example.lastbuildweek.controllers;


import com.example.lastbuildweek.entities.Comune;
import com.example.lastbuildweek.repositories.ComuneRepository;
import com.example.lastbuildweek.services.ComuneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comuni")
@Slf4j
@CrossOrigin(origins = "*")
public class ComuneController {

    @Autowired
    private ComuneService comuneService;

    @Autowired
    private ComuneRepository comuneRepository;

    // GET ALL
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Comune>> getById( ) throws Exception {

        return new ResponseEntity<>(
                comuneRepository.findAll(  ),
                HttpStatus.OK
        );
    }


    // RITORNA UN SINGOLO COMUNE PER NOME(PK)
    @GetMapping("/{nome}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Comune> getById( @PathVariable("nome") String nome ) throws Exception {

        return new ResponseEntity<>(
                comuneService.getByNome( nome ),
                HttpStatus.OK
        );
    }


    // CREATE
    @PostMapping("/new-raw")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Comune> create( @RequestBody Comune comune ) {

        try {

            comuneService.save( comune );

            return new ResponseEntity<>( comune, HttpStatus.OK ) ;

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }




    //UPDATE
    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public void update( @RequestBody Comune comune ) {

        try {

            comuneService.save( comune );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }
    }

    //DELETE
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
