package com.example.lastbuildweek.services;

import com.example.lastbuildweek.entities.Fattura;
import com.example.lastbuildweek.entities.StatoFattura;
import com.example.lastbuildweek.repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class FatturaService {
    @Autowired
    FatturaRepository fatturaRepository;

    public void save(Fattura comune){
        fatturaRepository.save(comune);
    };

    public Fattura getById(Long id) throws Exception {
        Optional<Fattura> fattura = fatturaRepository.findById(id);
        if ( fattura.isEmpty() )
            throw new Exception("Fattura not available");
        return fattura.get();
    }

    public void delete(Long id) throws Exception {
        Optional<Fattura> fatture = fatturaRepository.findById(id);
        if (fatture.isPresent()) {
            fatturaRepository.delete(fatture.get());
        } else {
            throw new Exception("Fattura non trovato");
        }
    }

    public void update(Fattura fattura) {
        fatturaRepository.save(fattura);
    }

    public Page<Fattura> getAllPaginate(Pageable p) {
        return fatturaRepository.findAll(p);
    }

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER CLIENTE ID(PK)
    public Page<Fattura> filterByClienteId(Long id, Pageable p) {
        return fatturaRepository.findFatturaByClienteId( id, p );
    }

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER STATO
    public Page<Fattura> filterFatturaByStatoFattura( String stato, Pageable pageable ) {
        return fatturaRepository.findFatturaByStatoFattura( StatoFattura.valueOf( stato ), pageable );
    }

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER DATA(LOCALDATE)
    public Page<Fattura> filterFatturaByDataLocal( LocalDate data, Pageable p) {
        return fatturaRepository.findFatturaByDataLocal( data, p );
    }

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER ANNO
    public Page<Fattura> filterFatturaByAnno( int anno, Pageable p) {
        return fatturaRepository.findFatturaByAnno( anno, p );
    }

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER RANGE DI IMPORTI
    public Page<Fattura> filterFatturaByRange(int dataIniziale, int dataFinale, Pageable p) {
        return fatturaRepository.findFatturaByRange( dataIniziale, dataFinale, p );
    }
}
