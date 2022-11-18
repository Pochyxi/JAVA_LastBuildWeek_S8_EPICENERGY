package com.example.lastbuildweek.services;

import com.example.lastbuildweek.entities.Comune;
import com.example.lastbuildweek.entities.Comune;
import com.example.lastbuildweek.entities.Provincia;
import com.example.lastbuildweek.repositories.ComuneRepository;
import com.example.lastbuildweek.utils.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
@Service
public class ComuneService {


    @Autowired
    ComuneRepository comuneRepository;

    @Autowired
    ProvinciaService provinciaService;

    public String addComuni() throws Exception {

        CSVReader reader = new CSVReader();

        int count = 0;

        for( String prov : reader.listComuni() ) {
            if(count != 0) {

                String[] line = prov.split( ";" );
                String provincia = line[ 3 ];
                String nomeComune = line[ 2 ];

                try {
                    Comune newComune = Comune.builder()
                            .nome(nomeComune)
                            .provincia( provinciaService.getByNome( provincia ) )
                            .build();

                    // Salvo l'entita nel database
                    comuneRepository.save(newComune);
                } catch( Exception e ) {
                    System.out.println("Error");
                }

            } else {
                count++;
            }

        }
        return "ok";
    }

    public void save(Comune comune){
        comuneRepository.save(comune);
    };

    public Comune getByNome(String id) throws Exception {
        Optional<Comune> comune = comuneRepository.findByNome(id);
        if ( comune.isEmpty() )
            throw new Exception("Comune not available");
        return comune.get();
    }

    public void delete(String id) throws Exception {
        Optional<Comune> comune = comuneRepository.findByNome(id);
        if (comune.isPresent()) {
            comuneRepository.delete(comune.get());
        } else {
            throw new Exception("Comune non trovato");
        }
    }

    public void update(Comune comune) {
        comuneRepository.save(comune);
    }

    public Page<Comune> getAllPaginate(Pageable p) {
        return comuneRepository.findAll(p);
    }



}
