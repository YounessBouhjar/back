package com.example.agent.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.agent.beans.ClientBean;

import java.util.List;

@FeignClient(name = "client-service")
public interface MicroserviceClientProxy{
	 @GetMapping("/client/all")
	    public ResponseEntity<List<ClientBean>> getAllClients () ;
    
	 @PutMapping("/client/updateClient")
	    public ResponseEntity<?> updateClient( @RequestBody ClientBean client);
    
	 @GetMapping("/client/findid/{id}")
	    public ResponseEntity<ClientBean> findClientById(@PathVariable("id") Long id);
    
	 @GetMapping("/client/findgsm/{gsm}")
	    public ResponseEntity<ClientBean> findClientByGSM(@PathVariable("gsm") String gsm);
    
	 @GetMapping("/client/findcin/{cin}")
	    public ResponseEntity<ClientBean> findClientByCin(@PathVariable("cin") String cin);

	 @PostMapping("/client/add")
	    public ResponseEntity<ClientBean> addClient(@RequestBody ClientBean client);
	 
	 @DeleteMapping("/client/delete/{id}")
	    public ResponseEntity<ClientBean> deleteClient(@PathVariable("id") Long id);
}
