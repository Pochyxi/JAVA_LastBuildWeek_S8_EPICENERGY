package com.example.lastbuildweek.repositories;

import com.example.lastbuildweek.entities.Fattura;
import com.example.lastbuildweek.entities.StatoFattura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface FatturaRepository extends JpaRepository<Fattura, Long> {

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER CLIENTE ID(PK)
    @Query(
            value = "select f from Fattura f where f.cliente.clienteId = :id"
    )
    Page<Fattura> findFatturaByClienteId( @Param("id") Long id, Pageable pageable );

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER STATO
    @Query("select f from Fattura f where f.statoFattura = :stato")
    Page<Fattura> findFatturaByStatoFattura( @Param( "stato" ) StatoFattura stato, Pageable pageable );

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER DATA(LOCALDATE)
    @Query(
            value = "select f from Fattura f where f.data = :data"
    )
    Page<Fattura> findFatturaByDataLocal( @Param("data") LocalDate data, Pageable pageable );

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER ANNO
    @Query(
            value = "select f from Fattura f where f.anno = :anno"
    )
    Page<Fattura> findFatturaByAnno( @Param("anno") int anno, Pageable pageable );

    // RITORNA UNA LISTA DI FATTURE FILTRATE PER RANGE DI IMPORTI
    @Query(
            value = "select f from Fattura f where f.importo >= :importoIniziale and f.importo <= :importoFinale"
    )
    Page<Fattura> findFatturaByRange( @Param("importoIniziale") int importoIniziale,
                                     @Param("importoFinale") int importoFinale, Pageable pageable );


}
