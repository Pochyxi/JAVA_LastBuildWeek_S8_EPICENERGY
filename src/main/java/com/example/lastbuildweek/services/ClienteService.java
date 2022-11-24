package com.example.lastbuildweek.services;

import com.example.lastbuildweek.entities.Cliente;
import com.example.lastbuildweek.entities.RagioneSociale;
import com.example.lastbuildweek.entities.User;
import com.example.lastbuildweek.repositories.ClienteRepository;
import com.example.lastbuildweek.repositories.UserRepository;
import com.example.lastbuildweek.utils.RequestModels.ClienteRequest;
import com.example.lastbuildweek.utils.ResponseModels.ClienteResponse;
import com.example.lastbuildweek.utils.RagioneSocialeParser;
import com.example.lastbuildweek.utils.ResponseModels.UserResponse;
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
    private UserRepository userRepository;

    @Autowired
    private IndirizzoService indirizzoService;

    // GETALL
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> getAllPaginate( Pageable p ) {
        return clienteRepository.findAll( p );
    }

    // GET BY ID
    public Cliente getById( Long id ) throws Exception {
        Optional<Cliente> cliente = clienteRepository.findById( id );
        if( cliente.isEmpty() )
            throw new Exception( "Cliente not available" );
        return cliente.get();
    }

    // CREATE AND SAVE
    public ClienteResponse createAndSave( ClienteRequest clienteRequest ) throws Exception {

        Cliente cliente = Cliente.builder()
                .partitaIva( clienteRequest.getPartitaIva() )
                .user( userService.getById( ( long ) clienteRequest.getUserId() ) )
                .indirizzoLegale( indirizzoService.getById( ( long ) clienteRequest.getIndirizzoLegaleId() ) )
                .indirizzoOperativo( indirizzoService.getById( ( long ) clienteRequest.getIndirizzoOperativoId() ) )
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

        clienteRepository.save( cliente );

        return ClienteResponse.parseCliente( cliente );
    }

    // CREATE
    public void save( Cliente cliente ) {
        clienteRepository.save( cliente );
    }

    public ClienteResponse updateResponse( ClienteRequest clienteRequest, Long id ) throws Exception {
        Optional<Cliente> clienteFind = clienteRepository.findById( id );

        if( clienteFind.isPresent() ) {
            Cliente c = new Cliente();
            c.setClienteId( id );
            c.setUser( clienteRequest.getUserId() == 0 ? clienteFind.get().getUser() :
                    userRepository.findById( ( long ) clienteRequest.getUserId() ).isPresent() ?
                            userRepository.findById( ( long ) clienteRequest.getUserId() ).get() : clienteFind.get().getUser() );
            c.setPartitaIva( clienteRequest.getPartitaIva() == 0 ? clienteFind.get().getPartitaIva() :
                    clienteRequest.getPartitaIva() );
            c.setIndirizzoLegale( clienteRequest.getIndirizzoLegaleId() == 0 ?
                    clienteFind.get().getIndirizzoLegale() : indirizzoService.getById( ( long ) clienteRequest.getIndirizzoLegaleId() ) );
            c.setIndirizzoOperativo( clienteRequest.getIndirizzoOperativoId() == 0 ?
                    clienteFind.get().getIndirizzoOperativo() : indirizzoService.getById( ( long ) clienteRequest.getIndirizzoOperativoId() ) );
            c.setEmail( clienteRequest.getEmail() == null ? clienteFind.get().getEmail() : clienteRequest.getEmail() );
            c.setPec( clienteRequest.getPec() == null ? clienteFind.get().getPec() : clienteRequest.getPec() );
            c.setEmailContatto( clienteRequest.getEmailContatto() == null ? clienteFind.get().getEmailContatto() :
                    clienteRequest.getEmailContatto() );
            c.setNomeContatto( clienteRequest.getNomeContatto() == null ? clienteFind.get().getNomeContatto() :
                    clienteRequest.getNomeContatto() );
            c.setCognomeContatto( clienteRequest.getCognomeContatto() == null ?
                    clienteFind.get().getCognomeContatto() : clienteRequest.getCognomeContatto() );
            c.setTelefonoContatto( clienteRequest.getTelefonoContatto() == null ?
                    clienteFind.get().getTelefonoContatto() : clienteRequest.getTelefonoContatto() );
            c.setFatturatoAnnuo( clienteRequest.getFatturatoAnnuo() == 0 ? clienteFind.get().getFatturatoAnnuo() :
                    clienteRequest.getFatturatoAnnuo() );
            c.setFatture( clienteFind.get().getFatture() );
            c.setRagioneSociale( clienteRequest.getRagioneSociale() == null ? clienteFind.get().getRagioneSociale() :
                    RagioneSociale.valueOf( clienteRequest.getRagioneSociale() ) );
            c.setDataInserimento( clienteFind.get().getDataInserimento() );
            c.setDataUltimoContatto( clienteFind.get().getDataUltimoContatto() );

            clienteRepository.save( c );
            return ClienteResponse.parseCliente( clienteFind.get() );
        } else {
            return null;
        }
    }

    // UPDATE
    public void update( Cliente cliente ) {
        clienteRepository.save( cliente );
    }

    // DELETE
    public void delete( Long id ) throws Exception {
        Optional<Cliente> cliente = clienteRepository.findById( id );
        if( cliente.isPresent() ) {
            clienteRepository.delete( cliente.get() );
        } else {
            throw new Exception( "Cliente non trovato" );
        }
    }

    ///////////////////////// QUERY PERSONALIZZATE/////////////////////////
    ///////////////////////////////////////////////////////////////////////


    ///////////////////////////////////// ORDER BY /////////////////////////////////

    // GET BY NOME CONTATTO
    public Page<Cliente> getByNomeContatto( Pageable p ) {
        return clienteRepository.findByNomeContatto( p );
    }

    // GET BY FATTURATO ANNUO
    public Page<Cliente> getByFatturatoAnnuo( Pageable p ) {
        return clienteRepository.findByFatturatoAnnuo( p );
    }

    // GET BY DATA INSERIMENTO
    public Page<Cliente> getByDataInserimento( Pageable p ) {
        return clienteRepository.findByDataInserimento( p );
    }

    // GET BY DATA ULTIMO CONTATTO
    public Page<Cliente> getByDataUltimoContatto( Pageable p ) {
        return clienteRepository.findByDataUltimoContatto( p );
    }

    // GET BY NOME PROVNCIA
    public Page<Cliente> getByNomeProvincia( Pageable p ) {
        return clienteRepository.findByNomeProvincia( p );
    }


    ////////////////////////////// FILTER BY //////////////////////////////////

    // GET BY FATTURATO PARAM
    public Page<Cliente> filterByFatturato( int fatturato, Pageable p ) {
        return clienteRepository.filterByFatturatoAnnuo( fatturato, p );
    }

    // GET BY DATA INSERIMENTO PARAM
    public Page<Cliente> filterByDataInserimento( LocalDate dataInserimento, Pageable p ) {
        return clienteRepository.filterByDataInserimento( dataInserimento, p );
    }

    // GET BY DATA ULTIMO CONTATTO PARAM
    public Page<Cliente> filterByDataUltimoContatto( LocalDate data, Pageable p ) {
        return clienteRepository.filterByDataUltimoContatto( data, p );
    }

    // GET BY NOME E COGNOME PARAMS
    public Page<Cliente> filterByNomeECognome( String nome, String cognome, Pageable p ) {
        return clienteRepository.filterByNomeECognome( nome, cognome, p );
    }


}
