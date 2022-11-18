package com.example.lastbuildweek.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    private int partitaIva;

    @OneToOne
    private User user;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "indirizzo_legale_id")
    private Indirizzo indirizzoLegale;

    @ManyToOne(cascade = { CascadeType.ALL })
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
    @Builder.Default
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Fattura> fatture = new java.util.ArrayList<>();

    public void addDataInserimento() {
        this.dataInserimento = LocalDate.now();
    }

}
