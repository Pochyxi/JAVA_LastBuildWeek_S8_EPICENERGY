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

    public String addProvincia() throws IOException {

        CSVReader reader = new CSVReader();
        //Il count mi serve per evitare che il primo elemento dell'array venga inserito nel database
        int count = 0;

        for( String prov : reader.listProvince() ) {
            if(count != 0) {
                // prov sarà un elemento dell'array (esempio: String prov = "Agrigento;AG;Sicilia")
                // splittando ulteriormente la stringa possiamo ottenere ["Agrigento", "AG", "Sicilia"]
                String[] line = prov.split( ";" );
                String sigla = line[ 0 ]; // "Agrigento"
                String provincia = line[ 1 ]; // "AG"
                String regione = line[ 2 ]; // "Sicilia"

                //Con i dati ottenuti creo la mia entità di tipo Provincia
                Provincia newProv = Provincia.builder()
                        .sigla( sigla )
                        .nome( provincia )
                        .regione( regione )
                        .build();

                // Salvo l'entita nel database
                provinciaRepository.save(newProv);

            } else {
                count++;
            }

        }
        return "ok";
    }

    public void save(Provincia p){
        provinciaRepository.save(p);
    };

    public Provincia getBySigla(String sigla) throws Exception {
        Optional< Provincia> provincia = provinciaRepository.findBySigla(sigla);
        if ( provincia.isEmpty() )
            throw new Exception("Provincia not available");
        return provincia.get();
    }

    public Provincia getByNome(String nome) throws Exception {
        Optional< Provincia> provincia = provinciaRepository.findByNome(nome);
        if ( provincia.isEmpty() )
            throw new Exception("Provincia not available");
        return provincia.get();
    }

    public void delete(String sigla) throws Exception {
        Optional< Provincia> provincia = provinciaRepository.findBySigla(sigla);
        if (provincia.isPresent()) {
            provinciaRepository.delete(provincia.get());
        } else {
            throw new Exception("Provincia non trovata");
        }
    }

    public void update( Provincia provincia) {
        provinciaRepository.save(provincia);
    }

    public Page<Provincia> getAllPaginate(Pageable p) {
        return provinciaRepository.findAll(p);
    }
}
