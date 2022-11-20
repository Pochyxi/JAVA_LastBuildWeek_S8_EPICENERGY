package com.example.lastbuildweek.entities;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "indirizzi")
public class Indirizzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indirizzo", nullable = false)
    private Long indirizzoId;

    private String via;
    private int civico;
    private int cap;

    @ManyToOne
    @JoinColumn(name = "comune")
    private Comune comune;


}
