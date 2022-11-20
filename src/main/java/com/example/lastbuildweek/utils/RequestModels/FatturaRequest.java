package com.example.lastbuildweek.utils.RequestModels;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FatturaRequest {

    private int anno;
    private int importo;
    private Long clienteId;
    private String statoFattura;


}
