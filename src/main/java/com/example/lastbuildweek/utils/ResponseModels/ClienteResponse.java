package com.example.lastbuildweek.utils.ResponseModels;

import com.example.lastbuildweek.entities.Cliente;
import com.example.lastbuildweek.entities.Indirizzo;
import com.example.lastbuildweek.entities.RagioneSociale;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClienteResponse {

    private Long clienteId;
    private int partitaIva;
    private String userUsername;
    private Indirizzo indirizzoLegale;
    private Indirizzo indirizzoOperativo;
    private String email;
    private String pec;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private Long telefonoContatto;
    private RagioneSociale ragioneSociale;
    private int fatturatoAnnuo;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;

    public static ClienteResponse parseCliente( Cliente cliente ) {
        return ClienteResponse.builder()
                .clienteId( cliente.getClienteId() )
                .partitaIva( cliente.getPartitaIva() )
                .userUsername( cliente.getUser().getUsername() )
                .indirizzoLegale( cliente.getIndirizzoLegale() )
                .indirizzoOperativo( cliente.getIndirizzoOperativo() )
                .email( cliente.getEmail() )
                .pec( cliente.getPec() )
                .emailContatto( cliente.getEmailContatto() )
                .nomeContatto( cliente.getNomeContatto() )
                .cognomeContatto( cliente.getCognomeContatto() )
                .telefonoContatto( cliente.getTelefonoContatto() )
                .ragioneSociale( cliente.getRagioneSociale() )
                .fatturatoAnnuo( cliente.getFatturatoAnnuo() )
                .dataInserimento( cliente.getDataInserimento() )
                .dataUltimoContatto( cliente.getDataUltimoContatto() )
                .build();

    }
}
