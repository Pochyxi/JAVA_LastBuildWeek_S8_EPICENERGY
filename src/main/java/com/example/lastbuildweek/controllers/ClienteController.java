package com.example.lastbuildweek.controllers;

import com.example.lastbuildweek.entities.*;
import com.example.lastbuildweek.services.*;
import com.example.lastbuildweek.utils.RequestModels.ClienteRequest;
import com.example.lastbuildweek.utils.ResponseModels.ClienteResponse;
import com.example.lastbuildweek.utils.ConverDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clienti")
@Slf4j
@CrossOrigin(origins = "*")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @CrossOrigin
    public List<Cliente> getAllClienti() {

        return clienteService.getAll();

    }

    // RITORNA UN SINGOLO CLIENTE PER ID(PK)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cliente> getById( @PathVariable Long id ) throws Exception {

        return new ResponseEntity<>(
                clienteService.getById( id ),
                HttpStatus.OK
        );
    }

    //RITORNA UNA PAGINAZIONE DI TUTTI I CLIENTI ORDINATI PER NOME

    @GetMapping("/nome/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> getByNomeContatto( Pageable p ) {
        return new ResponseEntity<>(
                clienteService.getByNomeContatto( p ),
                HttpStatus.OK
        );
    }


    //RITORNA UNA PAGINAZIONE DI TUTTI I CLIENTI ORDINATI PER FATTURATO ANNUO

    @GetMapping("/fatturato-annuo/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> getByFatturatoAnnuo( Pageable p ) {
        return new ResponseEntity<>(
                clienteService.getByFatturatoAnnuo( p ),
                HttpStatus.OK
        );
    }

    //RITORNA UNA PAGINAZIONE DI TUTTI I CLIENTI ORDINATI PER FATTURATO ANNUO
    @GetMapping("/data-inserimento/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> getByDataInserimento( Pageable p ) {
        return new ResponseEntity<>(
                clienteService.getByDataInserimento( p ),
                HttpStatus.OK
        );
    }

    @GetMapping("/data-ultimo-contatto/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> getByDataUltimoContatto( Pageable p ) {
        return new ResponseEntity<>(
                clienteService.getByDataUltimoContatto( p ),
                HttpStatus.OK
        );
    }

    //RITORNA UNA LISTA DI CLIENTI ORDINATI PER PROVINCIA
    @GetMapping("/provincia/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> getByNomeProvincia( Pageable p ) {
        return new ResponseEntity<>(
                clienteService.getByNomeProvincia( p ),
                HttpStatus.OK
        );
    }


    //RITORNA UNA LISTA DI CLIENTI FILTRATI PER FATTURATO ANNUO < DI UN PARAMETRO DATO
    @GetMapping("/fatturato/{fatturato}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> getByFatturato( @PathVariable("fatturato") int fatturato, Pageable p ) {
        return new ResponseEntity<>(
                clienteService.filterByFatturato( fatturato, p ),
                HttpStatus.OK
        );
    }

    //    //RITORNA UNA LISTA DI CLIENTI FILTRATI PER DATA INSERIMENTO
    @GetMapping("/filter-data-inserimento/{data}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> getByDataInserimento( @PathVariable("data") String stringData, Pageable p ) {

        return new ResponseEntity<>(
                clienteService.filterByDataInserimento( ConverDate.convertDate( stringData ), p ),
                HttpStatus.OK
        );
    }

    // RITORNA UNA LISTA DI CLIENTI FILTRATI PER DATA ULTIMO CONTATTO
    @GetMapping("/filter-data-ultimo-contatto/{data}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> getByDataUltimoInserimento( @PathVariable("data") String stringData, Pageable p ) {

        return new ResponseEntity<>(
                clienteService.filterByDataUltimoContatto( ConverDate.convertDate( stringData ), p ),
                HttpStatus.OK
        );
    }

    // RITORNA UNA LISTA DI CLIENTI FILTRATI PER NOME E COGNOME
    @GetMapping("/filter-nome-cognome/{nome}/{cognome}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Cliente>> filterByNomeECognome( @PathVariable("nome") String nome,
                                                               @PathVariable("cognome") String cognome,
                                                               Pageable p ) {
        return new ResponseEntity<>(
                clienteService.filterByNomeECognome( nome, cognome, p ),
                HttpStatus.OK
        );
    }

    // AGGIUNGI UN NUOVO CLIENTE CON IL BODY COME RICHIESTA
    @PostMapping("/new-raw")
    @PreAuthorize("hasRole('ADMIN')")
    public ClienteResponse create( @RequestBody ClienteRequest clienteRequest ) {

        try {

             return clienteService.createAndSave( clienteRequest );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

        return null;

    }

    //AGGIORNA LE PROPRIETA' DI UN CLIENTE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponse> update( @RequestBody ClienteRequest clienteRequest, @PathVariable("id") Long id  ) {

        try {

            return new ResponseEntity<>( clienteService.updateResponse( clienteRequest, id ), HttpStatus.OK );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById( @PathVariable Long id ) {

        try {

            clienteService.delete( id );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

    }
}
