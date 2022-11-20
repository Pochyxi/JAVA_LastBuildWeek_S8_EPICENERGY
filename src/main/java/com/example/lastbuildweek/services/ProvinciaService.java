package com.example.lastbuildweek.services;

import com.example.lastbuildweek.entities.Provincia;
import com.example.lastbuildweek.repositories.ProvinciaRepository;
import com.example.lastbuildweek.utils.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProvinciaService {

    @Autowired
    ProvinciaRepository provinciaRepository;

    // GET BY SIGLA
    public Provincia getBySigla( String sigla ) throws Exception {
        Optional<Provincia> provincia = provinciaRepository.findBySigla( sigla );
        if( provincia.isEmpty() )
            throw new Exception( "Provincia not available" );
        return provincia.get();
    }

    // GET BY NOME
    public Provincia getByNome( String nome ) throws Exception {
        Optional<Provincia> provincia = provinciaRepository.findByNome( nome );
        if( provincia.isEmpty() )
            throw new Exception( "Provincia not available" );
        return provincia.get();
    }

    // CREATE
    public void save( Provincia p ) {
        provinciaRepository.save( p );
    }

    // UPDATE
    public void update( Provincia provincia ) {
        provinciaRepository.save( provincia );
    }

    // DELETE
    public void delete( String sigla ) throws Exception {
        Optional<Provincia> provincia = provinciaRepository.findBySigla( sigla );
        if( provincia.isPresent() ) {
            provinciaRepository.delete( provincia.get() );
        } else {
            throw new Exception( "Provincia non trovata" );
        }
    }
}
