package com.example.lastbuildweek.controllers;

import com.example.lastbuildweek.entities.Provincia;
import com.example.lastbuildweek.repositories.ProvinciaRepository;
import com.example.lastbuildweek.services.ProvinciaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/province")
@Slf4j
@CrossOrigin(origins = "*")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    // GET BY NOME
    @GetMapping("/nome/{provincia}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Provincia>> getByNome( @PathVariable("provincia") String provincia) {
        return new ResponseEntity<>( provinciaRepository.findByNomeContainingIgnoreCase(provincia), HttpStatus.OK );
    }

    // GET BY SIGLA
    @GetMapping("/{sigla}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Provincia> get( @PathVariable("sigla") String sigla ) throws Exception {

        return new ResponseEntity<>(
                provinciaService.getBySigla( sigla ),
                HttpStatus.OK
        );
    }


    // CREATE
    @PostMapping("/new-raw")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Provincia> create( @RequestBody Provincia provincia ) {

        try {

            provinciaService.save( provincia );

            return new ResponseEntity<>( provincia, HttpStatus.OK );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

        return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );

    }




    //UPDATE
    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public void update( @RequestBody Provincia provincia ) {

        try {

            provinciaService.save( provincia );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }
    }


    // DELETE BY SIGLA
    @DeleteMapping("/delete/{sigla}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById( @PathVariable("sigla") String sigla ) {

        try {

            provinciaService.delete( sigla );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

    }
}
