package com.example.lastbuildweek.services;

import com.example.lastbuildweek.entities.Cliente;
import com.example.lastbuildweek.repositories.ClienteRepository;
import com.example.lastbuildweek.utils.ClienteRequest;
import com.example.lastbuildweek.utils.RagioneSocialeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IndirizzoLegaleService indirizzoLegaleService;

    @Autowired
    private IndirizzoOperativoService indirizzoOperativoService;

    public void save( Cliente cliente ) {
        clienteRepository.save( cliente );
    }

    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    public Cliente createAndUpdate( ClienteRequest clienteRequest, int clienteId ) throws Exception {
        Optional<Cliente> clienteFind = clienteRepository.findById( ( long ) clienteId );
        if( clienteFind.isPresent() ) {
            Cliente cliente = Cliente.builder()
                    .clienteId( clienteFind.get().getClienteId() )
                    .partitaIva( clienteRequest.getPartitaIva() == 0 ? clienteFind.get().getPartitaIva() : clienteRequest.getPartitaIva() )
                    .user( clienteRequest.getUserId() == 0 ? clienteFind.get().getUser() : userService.getById( ( long ) clienteRequest.getUserId() ) )
                    .indirizzoLegale( clienteRequest.getIndirizzoLegaleId() == 0 ? clienteFind.get().getIndirizzoLegale() : indirizzoLegaleService.getById( ( long ) clienteRequest.getIndirizzoLegaleId() ) )
                    .indirizzoOperativo( clienteRequest.getIndirizzoOperativoId() == 0 ?
                            clienteFind.get().getIndirizzoOperativo() : indirizzoOperativoService.getById( ( long ) clienteRequest.getIndirizzoOperativoId() ) )
                    .email( clienteRequest.getEmail() == null ? clienteFind.get().getEmail() : clienteRequest.getEmail() )
                    .pec( clienteRequest.getPec() == null ? clienteFind.get().getPec() : clienteRequest.getPec() )
                    .emailContatto( clienteRequest.getEmailContatto() == null ? clienteFind.get().getEmailContatto() : clienteRequest.getEmailContatto() )
                    .nomeContatto( clienteRequest.getNomeContatto() == null ? clienteFind.get().getNomeContatto() : clienteRequest.getNomeContatto() )
                    .cognomeContatto( clienteRequest.getCognomeContatto() == null ? clienteFind.get().getCognomeContatto() : clienteRequest.getCognomeContatto() )
                    .telefonoContatto( clienteRequest.getTelefonoContatto() == null ?
                            clienteFind.get().getTelefonoContatto() : clienteRequest.getTelefonoContatto() )
                    .ragioneSociale( clienteRequest.getRagioneSociale() == null ? clienteFind.get().getRagioneSociale() : RagioneSocialeParser.parse( clienteRequest.getRagioneSociale() ) )
                    .fatturatoAnnuo( clienteRequest.getFatturatoAnnuo() == 0 ? clienteFind.get().getFatturatoAnnuo() : clienteRequest.getFatturatoAnnuo() )
                    .dataInserimento( clienteFind.get().getDataInserimento() )
                    .dataUltimoContatto( clienteFind.get().getDataUltimoContatto() )
                .build();
            clienteRepository.save( cliente );
            return cliente;
        } else {
            throw new Exception( "Cliente not available" );
        }

    }

    ;

    public Cliente createAndSave( ClienteRequest clienteRequest ) throws Exception {

        Cliente cliente = Cliente.builder()
                .partitaIva( clienteRequest.getPartitaIva() )
                .user( userService.getById( ( long ) clienteRequest.getUserId() ) )
                .indirizzoLegale( indirizzoLegaleService.getById( ( long ) clienteRequest.getIndirizzoLegaleId() ) )
                .indirizzoOperativo( indirizzoOperativoService.getById( ( long ) clienteRequest.getIndirizzoOperativoId() ) )
                .email( clienteRequest.getEmail() )
                .pec( clienteRequest.getPec() )
                .emailContatto( clienteRequest.getEmailContatto() )
                .nomeContatto( clienteRequest.getNomeContatto() )
                .cognomeContatto( clienteRequest.getCognomeContatto() )
                .telefonoContatto( clienteRequest.getTelefonoContatto() )
                .ragioneSociale( RagioneSocialeParser.parse( clienteRequest.getRagioneSociale() ) )
                .fatturatoAnnuo( clienteRequest.getFatturatoAnnuo() )
                .dataInserimento( LocalDate.now() )
                .dataUltimoContatto( LocalDate.now() )
                .build();

        return clienteRepository.save( cliente );
    }

    ;


    public Cliente getById( Long id ) throws Exception {
        Optional<Cliente> cliente = clienteRepository.findById( id );
        if( cliente.isEmpty() )
            throw new Exception( "Cliente not available" );
        return cliente.get();
    }

    public void delete( Long id ) throws Exception {
        Optional<Cliente> cliente = clienteRepository.findById( id );
        if( cliente.isPresent() ) {
            clienteRepository.delete( cliente.get() );
        } else {
            throw new Exception( "Cliente non trovato" );
        }
    }

    public void update( Cliente cliente ) {
        clienteRepository.save( cliente );
    }

    public Page<Cliente> getAllPaginate( Pageable p ) {
        return clienteRepository.findAll( p );
    }

    public Page<Cliente> getByNomeContatto( Pageable p ) {
        return clienteRepository.findByNomeContatto( p );
    }


    public Page<Cliente> getByFatturatoAnnuo( Pageable p ) {
        return clienteRepository.findByFatturatoAnnuo( p );
    }


    public Page<Cliente> getByDataInserimento( Pageable p ) {
        return clienteRepository.findByDataInserimento( p );
    }

    public Page<Cliente> getByDataUltimoContatto( Pageable p ) {
        return clienteRepository.findByDataUltimoContatto( p );
    }

    public Page<Cliente> getByNomeProvincia( Pageable p ) {
        return clienteRepository.findByNomeProvincia( p );
    }

    ////////////////////////////////////////////////////////////////

    public Page<Cliente> filterByFatturato( int fatturato, Pageable p ) {
        return clienteRepository.filterByFatturatoAnnuo( fatturato, p );
    }

    public Page<Cliente> filterByDataInserimento( LocalDate dataInserimento, Pageable p ) {
        return clienteRepository.filterByDataInserimento( dataInserimento, p );
    }

    public Page<Cliente> filterByDataUltimoContatto( LocalDate data, Pageable p ) {
        return clienteRepository.filterByDataUltimoContatto( data, p );
    }

    public Page<Cliente> filterByNomeECognome( String nome, String cognome, Pageable p ) {
        return clienteRepository.filterByNomeECognome( nome, cognome, p );
    }


}
