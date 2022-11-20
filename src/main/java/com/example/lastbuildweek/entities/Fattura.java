package com.example.lastbuildweek.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "fatture")
public class Fattura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero", nullable = false)
    private Long numero;

    private int anno;

    private LocalDate data;

    private int importo;

    private StatoFattura statoFattura;

    @ManyToOne
    @JoinColumn(name = "cliente_partita_iva")
    @JsonManagedReference
    private Cliente cliente;

}
