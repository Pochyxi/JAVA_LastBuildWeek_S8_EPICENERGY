package com.example.lastbuildweek.utils.RequestModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IndirizzoRequest {
    private Long id;
    private String via;
    private int civico;
    private int cap;
    private String nomeComune;
}
