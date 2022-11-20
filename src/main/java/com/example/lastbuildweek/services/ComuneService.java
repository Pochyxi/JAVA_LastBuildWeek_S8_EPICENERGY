package com.example.lastbuildweek.services;

import com.example.lastbuildweek.entities.Comune;
import com.example.lastbuildweek.entities.Provincia;
import com.example.lastbuildweek.repositories.ComuneRepository;
import com.example.lastbuildweek.utils.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ComuneService {


    @Autowired
    ComuneRepository comuneRepository;

    @Autowired
    ProvinciaService provinciaService;

    // GET BY NOME
    public Comune getByNome(String id) throws Exception {
        Optional<Comune> comune = comuneRepository.findByNome(id);
        if ( comune.isEmpty() )
            throw new Exception("Comune not available");
        return comune.get();
    }

    // CREATE
    public void save(Comune comune){
        comuneRepository.save(comune);
    };

    // UPDATE
    public void update(Comune comune) {
        comuneRepository.save(comune);
    }


    // DELETE
    public void delete(String id) throws Exception {
        Optional<Comune> comune = comuneRepository.findByNome(id);
        if (comune.isPresent()) {
            comuneRepository.delete(comune.get());
        } else {
            throw new Exception("Comune non trovato");
        }
    }

    // METODO AUTOMATICO LANCIATO ALL'AVVIO DELL'APP NEL CASO IN CUI NEL DATABASE NON VENGA TROVATO IL COMUNE NAPOLI
    // QUINDI SI PRESUPPONE CHE NEL DATABASE NON CI SIANO COMUNI
    // DUNQUE SI PROVVEDERA' AD AGGIUNGERLI TUTTI
    public void addComuni() throws Exception {

        CSVReader reader = new CSVReader();

        int count1 = 0;

        for( String prov : reader.listProvince() ) {
            if( count1 != 0 ) {
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
                provinciaService.save( newProv );

            } else {
                count1++;
            }
        }

        int count2 = 0;

        for( String com : reader.listComuni() ) {
            if( count2 != 0 ) {

                String[] line = com.split( ";" );
                String provincia = line[ 3 ];
                String nomeComune = line[ 2 ];

                try {
                    Comune newComune = Comune.builder()
                            .nome( nomeComune )
                            .provincia( provinciaService.getByNome( provincia ) )
                            .build();

                    // Salvo l'entita nel database
                    comuneRepository.save( newComune );

                } catch( Exception e ) {
                    System.out.println( "Error" );
                }

            } else {
                count2++;
            }
        }
    }

}
