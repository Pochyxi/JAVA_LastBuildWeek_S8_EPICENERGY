package com.example.lastbuildweek.controllers;

import com.example.lastbuildweek.entities.*;
import com.example.lastbuildweek.repositories.ClienteRepository;
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

    @Autowired
    private ClienteRepository clienteRepository;

    //GET ALL
    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @CrossOrigin
    public ResponseEntity<List<Cliente>> getAllClienti() {

        return new ResponseEntity<>( clienteService.getAll(), HttpStatus.OK );

    }

    @GetMapping("/pageable")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @CrossOrigin
    public ResponseEntity<Page<Cliente>> getAllClientiPageable(Pageable p) {

        return new ResponseEntity<>( clienteService.getAllPaginate(p), HttpStatus.OK );

    }

    // GET
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Cliente> getById( @PathVariable Long id ) throws Exception {

        return new ResponseEntity<>(
                clienteService.getById( id ),
                HttpStatus.OK
        );
    }

    // CREATE
    @PostMapping("/new-raw")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ClienteResponse> create( @RequestBody ClienteRequest clienteRequest ) {

        try {

             return new ResponseEntity<>( clienteService.createAndSave( clienteRequest ), HttpStatus.OK );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

        return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );

    }

    //UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ClienteResponse> update( @RequestBody ClienteRequest clienteRequest, @PathVariable("id") Long id  ) {

        try {

            return new ResponseEntity<>( clienteService.updateResponse( clienteRequest, id ), HttpStatus.OK );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
    }


    //DELETE
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void deleteById( @PathVariable Long id ) {

        try {

            clienteService.delete( id );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

    }

    ///////////////////////// QUERY PERSONALIZZATE/////////////////////////
    ///////////////////////////////////////////////////////////////////////


    // RITORNA UNA LISTA DI CLIENTI ORDINATI PER USER ID
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Cliente>> getClietiByUserId( @PathVariable("id") Long id ) {
        return new ResponseEntity<>(
                clienteRepository.findClienteByUserId( id ),
                HttpStatus.OK
        );
    }

    // RITORNA UNA PAGINAZIONE DI TUTTI I CLIENTI ORDINATI PER NOME
    @GetMapping("/nome/")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Cliente>> getByNomeContatto( Pageable p ) {
        return new ResponseEntity<>(
                clienteService.getByNomeContatto( p ),
                HttpStatus.OK
        );
    }


    //RITORNA UNA PAGINAZIONE DI TUTTI I CLIENTI ORDINATI PER FATTURATO ANNUO

    @GetMapping("/fatturato-annuo/")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Cliente>> getByFatturatoAnnuo( Pageable p ) {
        return new ResponseEntity<>(
                clienteService.getByFatturatoAnnuo( p ),
                HttpStatus.OK
        );
    }

    //RITORNA UNA PAGINAZIONE DI TUTTI I CLIENTI ORDINATI PER FATTURATO ANNUO
    @GetMapping("/data-inserimento/")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Cliente>> getByDataInserimento( Pageable p ) {
        return new ResponseEntity<>(
                clienteService.getByDataInserimento( p ),
                HttpStatus.OK
        );
    }

    @GetMapping("/data-ultimo-contatto/")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Cliente>> getByDataUltimoContatto( Pageable p ) {
        return new ResponseEntity<>(
                clienteService.getByDataUltimoContatto( p ),
                HttpStatus.OK
        );
    }

    //RITORNA UNA LISTA DI CLIENTI ORDINATI PER PROVINCIA
    @GetMapping("/provincia/")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Cliente>> getByNomeProvincia( Pageable p ) {
        return new ResponseEntity<>(
                clienteService.getByNomeProvincia( p ),
                HttpStatus.OK
        );
    }


    //RITORNA UNA LISTA DI CLIENTI FILTRATI PER FATTURATO ANNUO < DI UN PARAMETRO DATO
    @GetMapping("/fatturato/{fatturato}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Cliente>> getByFatturato( @PathVariable("fatturato") int fatturato, Pageable p ) {
        return new ResponseEntity<>(
                clienteService.filterByFatturato( fatturato, p ),
                HttpStatus.OK
        );
    }

    //    //RITORNA UNA LISTA DI CLIENTI FILTRATI PER DATA INSERIMENTO
    @GetMapping("/filter-data-inserimento/{data}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Cliente>> getByDataInserimento( @PathVariable("data") String stringData, Pageable p ) {

        return new ResponseEntity<>(
                clienteService.filterByDataInserimento( ConverDate.convertDate( stringData ), p ),
                HttpStatus.OK
        );
    }

    // RITORNA UNA LISTA DI CLIENTI FILTRATI PER DATA ULTIMO CONTATTO
    @GetMapping("/filter-data-ultimo-contatto/{data}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Cliente>> getByDataUltimoInserimento( @PathVariable("data") String stringData, Pageable p ) {

        return new ResponseEntity<>(
                clienteService.filterByDataUltimoContatto( ConverDate.convertDate( stringData ), p ),
                HttpStatus.OK
        );
    }

    // RITORNA UNA LISTA DI CLIENTI FILTRATI PER NOME E COGNOME
    @GetMapping("/filter-nome-cognome/{nome}/{cognome}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Cliente>> filterByNomeECognome( @PathVariable("nome") String nome,
                                                               @PathVariable("cognome") String cognome,
                                                               Pageable p ) {
        return new ResponseEntity<>(
                clienteService.filterByNomeECognome( nome, cognome, p ),
                HttpStatus.OK
        );
    }
}
