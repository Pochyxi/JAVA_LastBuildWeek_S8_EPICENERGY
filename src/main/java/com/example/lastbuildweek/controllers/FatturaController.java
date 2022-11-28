package com.example.lastbuildweek.controllers;

import com.example.lastbuildweek.entities.Fattura;
import com.example.lastbuildweek.entities.StatoFattura;
import com.example.lastbuildweek.repositories.FatturaRepository;
import com.example.lastbuildweek.services.ClienteService;
import com.example.lastbuildweek.services.FatturaService;
import com.example.lastbuildweek.utils.ConverDate;
import com.example.lastbuildweek.utils.RequestModels.FatturaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/fatture")
@Slf4j
@CrossOrigin(origins = "*")
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FatturaRepository fatturaRepository;

    // GET ALL
    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Fattura>> getAll() {
        return new ResponseEntity<>(fatturaRepository.findAll(), HttpStatus.OK);
    }

    // GET ALL AND PAGINATE
    @GetMapping("/pageable")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Fattura>> getAllPageable( Pageable p) {
        return new ResponseEntity<>(fatturaService.getAllPaginate( p ), HttpStatus.OK);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Fattura> get(@PathVariable("id") Long id ) throws Exception {

        return new ResponseEntity<>(
                fatturaService.getById( id ),
                HttpStatus.OK
        );
    }




    // CREATE
    @PostMapping("/new-raw")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Fattura> create( @RequestBody FatturaRequest fatturaRequest ) {

        try {

            Fattura fattura = Fattura.builder()
                    .anno(fatturaRequest.getAnno())
                    .importo(fatturaRequest.getImporto())
                    .statoFattura( StatoFattura.valueOf( fatturaRequest.getStatoFattura() ) )
                    .cliente(clienteService.getById(fatturaRequest.getClienteId()))
                    .data(LocalDate.now())
                    .build();

            fatturaService.save( fattura );

            return new ResponseEntity<>( fattura, HttpStatus.OK ) ;

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }




    //UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Fattura> update( @RequestBody FatturaRequest fatturaRequest, @PathVariable("id") Long id ) {

        try {

            return fatturaService.update( fatturaRequest, id );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void deleteById( @PathVariable("id") Long id ) {

        try {

            fatturaService.delete( id );

        } catch( Exception e ) {

            log.error( e.getMessage() );

        }

    }

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER CLIENTE ID(PK)
    @GetMapping("/cliente/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Fattura>> get( @PathVariable("id") Long id, Pageable p ) throws Exception {

        return new ResponseEntity<>(
                fatturaService.filterByClienteId( id, p ),
                HttpStatus.OK
        );
    }

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER STATO
    @GetMapping("/stato/{stato}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Fattura>> getFatturaByStatoFattura( @PathVariable("stato") String stato, Pageable p ) {

        return new ResponseEntity<>(
                fatturaService.filterFatturaByStatoFattura( stato, p ),
                HttpStatus.OK
        );
    }

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER DATA(LOCALDATE)
    @GetMapping("/data/{data}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Fattura>> getFatturaByDataLocal( @PathVariable("data") String data, Pageable p ) {

        return new ResponseEntity<>(
                fatturaService.filterFatturaByDataLocal( ConverDate.convertDate( data ), p ),
                HttpStatus.OK
        );
    }

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER ANNO
    @GetMapping("/anno/{anno}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Fattura>> getFatturaByAnno( @PathVariable("anno") int anno, Pageable p ) {

        return new ResponseEntity<>(
                fatturaService.filterFatturaByAnno( anno, p ),
                HttpStatus.OK
        );
    }

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER RANGE DI IMPORTI
    @GetMapping("/range/inizio/{inizio}/fine/{fine}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Page<Fattura>> getFatturaByRange( @PathVariable("inizio") int inizio,
                                                            @PathVariable("fine") int fine, Pageable p ) {

        return new ResponseEntity<>(
                fatturaService.filterFatturaByRange( inizio, fine, p ),
                HttpStatus.OK
        );
    }

}
