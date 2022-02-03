package com.example.client.controller;



import com.example.client.beans.BeneficiaireBean;
import com.example.client.model.Client;
import com.example.client.proxies.MicroserviceBeneficiaireProxy;
import com.example.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    @Autowired
    MicroserviceBeneficiaireProxy microserviceBeneficiaireProxy;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients () {
        List<Client> clients = clientService.findAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Client> addClient(@RequestBody Client client) throws Exception{
        Client client1 = clientService.addClient(client);
        return new ResponseEntity<>(client1, HttpStatus.CREATED);
    }
    @GetMapping("/findgsm/{gsm}")
    public ResponseEntity<Client> findClientByGSM(@PathVariable("gsm") String gsm){
        Client client=clientService.findClientBynumGSM(gsm);
        return new ResponseEntity<>(client,HttpStatus.OK);
    }
    @GetMapping("/findcin/{cin}")
    public ResponseEntity<Client> findClientByCin(@PathVariable("cin") String cin){
        Client client=clientService.findClientByCin(cin);
        return new ResponseEntity<>(client,HttpStatus.OK);
    }
    @GetMapping("/findid/{id}")
    public ResponseEntity<Client> findClientById(@PathVariable("id") Long id){
        Client client=clientService.findClientById(id);
        return new ResponseEntity<>(client,HttpStatus.OK);
    }
    @GetMapping("/beneficiaire/all")
    public ResponseEntity<List<BeneficiaireBean>> getAllBeneficiaires () {
        return  microserviceBeneficiaireProxy.getAllBeneficiaires();
    }

    
    @PutMapping("/updateClient/{clientId}")
    public ResponseEntity<Client> updateClient(@PathVariable("clientId")Long clientId,@RequestBody Client client) throws Exception {
    	Client updateClient = clientService.updateClient(clientId,client);
        return new ResponseEntity<>(updateClient, HttpStatus.OK);
    }
    
    
}
