package com.example.client.service;




import com.example.client.exception.ClientNotFoundException;
import com.example.client.model.Client;
import com.example.client.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
	private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public List<Client> findAllClients(){
        return clientRepository.findAll();
    }
    public Client addClient(Client client){         
    	client.setPassword(new BCryptPasswordEncoder().encode(client.getPassword()));
    	client.setRole("Client");
    	return clientRepository.save(client);
    }
    
    public Client findClientBynumGSM(String numGSM){
        return clientRepository.findBynumGSM(numGSM);
    }
    public Client findClientById(Long id){
        return clientRepository.findByClientId(id);
    }
    public Client findClientByCin(String cin){
        return clientRepository.findBycin(cin);
    }
    public Client updateClient(Client client)throws ClientNotFoundException {
    	Client clientFromDB = clientRepository.findById(client.getClientId()).orElse(null);
        if (clientFromDB == null)
            throw new ClientNotFoundException("aucun client avec cette id");
        client.setClientId(clientFromDB.getClientId());
        client.setPassword(new BCryptPasswordEncoder().encode(client.getPassword()));
        return clientRepository.save(client);
    }
    

    
    
    public Long deleteClient(Long id)  {
        Client clt = clientRepository.findByClientId(id);
        clientRepository.delete(clt);
        return id;
    }

    
   
}
