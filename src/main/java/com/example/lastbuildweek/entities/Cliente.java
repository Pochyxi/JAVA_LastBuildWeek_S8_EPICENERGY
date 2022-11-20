package com.example.lastbuildweek.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "clienti")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    private int partitaIva;


    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "indirizzo_legale_id")
    private Indirizzo indirizzoLegale;

    @ManyToOne
    @JoinColumn(name = "indirizzo_operativo_id")
    private Indirizzo indirizzoOperativo;

    private String email;
    private String pec;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private Long telefonoContatto;
    private int fatturatoAnnuo;

    private LocalDate dataInserimento ;
    private LocalDate dataUltimoContatto;

    @Enumerated(EnumType.STRING)
    private RagioneSociale ragioneSociale;

    @ToString.Exclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Fattura> fatture;

}
