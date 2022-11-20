package com.example.lastbuildweek.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "provincie")
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long provinciaId;

    private String nome;


    @Column(unique = true)
    private String sigla;

    private String regione;

    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comune> comuni;

}
