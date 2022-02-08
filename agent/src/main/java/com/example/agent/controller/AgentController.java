package com.example.agent.controller;

import com.example.agent.beans.NotificationBean;
import com.example.agent.beans.ClientBean;
import com.example.agent.beans.CompteBean;
import com.example.agent.beans.TransfertBean;
import com.example.agent.exception.AgentNotFoundException;
import com.example.agent.model.Agent;
import com.example.agent.proxies.MicroserviceClientProxy;
import com.example.agent.proxies.MicroserviceCompteProxy;
import com.example.agent.proxies.MicroserviceTransfertProxy;
import com.example.agent.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/agent")

public class AgentController {
    private final AgentService agentService;
    @Autowired
    MicroserviceCompteProxy microserviceCompteProxy;
    @Autowired
    MicroserviceTransfertProxy microserviceTransfertProxy;

    @Autowired
    MicroserviceClientProxy microserviceClientProxy;
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }
    
  	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public String log()
	{	
		return "Welcome";
	
	}
    @GetMapping("/all")
    public ResponseEntity<List<Agent>> getAllAgent () {
        List<Agent> agents = agentService.getAllAgent();
        return new ResponseEntity<List<Agent>>(agents, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Agent> getAgentByEmail (@PathVariable("email") String email) {
        Agent agent=agentService.getAgentByEmail(email);
        return new ResponseEntity<Agent>(agent,HttpStatus.OK);
    }
    @GetMapping("/agentid/{id}")
    public ResponseEntity<Agent> getAgentByID (@PathVariable("id") Long id) {
        Agent agent=agentService.getAgentById(id);
        return new ResponseEntity<Agent>(agent,HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Agent> addAgent(@RequestBody Agent agent) throws Exception{
        Agent newAgent = agentService.addAgent(agent);
        return new ResponseEntity<Agent>(newAgent,HttpStatus.CREATED);
    }
	@PutMapping("/update")
    public ResponseEntity<?> update( @RequestBody Agent agent) throws Exception {
        if (agent == null)
            return ResponseEntity.badRequest().body("The provided agent is not valid");
        return ResponseEntity
                .ok()
                .body(agentService.updateAgent(agent));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Agent> deleteAgent(@PathVariable("id") Long id) throws Exception{
        agentService.deleteAgent(id);
        return new ResponseEntity<Agent>(HttpStatus.OK);
    }
    ///compte infos
    @GetMapping("/compte/all")
    ResponseEntity<List<CompteBean>> getAllCompte (){
        return microserviceCompteProxy.getAllCompte();
    }
    //find compte infos by nom client
    @GetMapping("/compte/find/{nomClient}")
    ResponseEntity<CompteBean> findCompteByNomClient(@PathVariable("nomClient") String nomClient){
        return microserviceCompteProxy.findCompteByNomClient(nomClient);
    }
    //find compte infos by numero compte
    @GetMapping("/compte/find/{numCompte}")
    ResponseEntity<CompteBean> findCompteBynumCompte(@PathVariable("numCompte") String numCompte){
        return microserviceCompteProxy.findCompteBynumCompte(numCompte);
    }
    ///add compte from agent
    @PostMapping("/compte/add")
    ResponseEntity<CompteBean> addCompte(@RequestBody CompteBean compte){
        return microserviceCompteProxy.addCompte(compte);
    }
    // update solde compte
    @PatchMapping("/compte/update/{id}/{solde}")
    public ResponseEntity<CompteBean> updateCompte(@PathVariable("id") Long id,@PathVariable("solde") float solde){
        return microserviceCompteProxy.updateCompte(id,solde);
    }

    @GetMapping("/client/all")
    public ResponseEntity<List<ClientBean>> getAllClients () {
		return microserviceClientProxy.getAllClients();
    }
    @PostMapping("/client/add")
    public ResponseEntity<ClientBean> addClient(@RequestBody ClientBean client){
    	return microserviceClientProxy.addClient(client);
    }
    
    @DeleteMapping("/client/delete/{id}")
    public ResponseEntity<ClientBean> deleteClient(@PathVariable("id") Long id){
    	return microserviceClientProxy.deleteClient(id);
    }
    
    @GetMapping("/compte/findClient/{idClient}")
    public ResponseEntity<List<CompteBean>> getCompteByIdClient(@PathVariable("idClient") Long idClient){
    	return microserviceCompteProxy.getCompteByIdClient(idClient);
    }
    
    
    @PutMapping("/client/updateClient")
    public ResponseEntity<?> updateClient( @RequestBody ClientBean client){
    	return microserviceClientProxy.updateClient(client);
    }
	 
	 @GetMapping("/client/findid/{id}")
	    public ResponseEntity<ClientBean> findClientById(@PathVariable("id") Long id){
		 return microserviceClientProxy.findClientById(id);
	 }

	 @GetMapping("/compte/findAgent/{idAgent}")
	    public ResponseEntity<CompteBean> getCompteByIdAgent(@PathVariable("idAgent") Long idAgent){
	    	return microserviceCompteProxy.getCompteByIdAgent(idAgent);
	    }
	 @GetMapping("/compte/findAdmin/{idAdmin}")
	    public ResponseEntity<CompteBean> getCompteByIdAdmin(@PathVariable("idAdmin") Long idAdmin){
	    	return microserviceCompteProxy.getCompteByIdAdmin(idAdmin);
	    }
	 
	 @GetMapping("transfert/findAgent/{id}")
	    public ResponseEntity<List<TransfertBean>> getTransfertByIdAgent(@PathVariable("id") Long id) {
		return microserviceTransfertProxy.getTransfertByIdAgent(id);
	}
	 
	 @GetMapping("/client/findcin/{cin}")
	    public ResponseEntity<ClientBean> findClientByCin(@PathVariable("cin") String cin){
		 return microserviceClientProxy.findClientByCin(cin);
	 }
	  @PostMapping("/transfert/add")
	    public ResponseEntity<TransfertBean> addtransfert(@RequestBody TransfertBean transfert){
	    	return microserviceTransfertProxy.addtransfert(transfert);
	    }
	  @PutMapping("/compte/updateSolde/{nomClient}")
		public ResponseEntity<CompteBean> updateSolde(@PathVariable("nomClient") String nomClient,@RequestParam(required = true) float solde){
	    	return microserviceCompteProxy.updateSolde(nomClient, solde);
	    }
	    @PostMapping("/notification/send")
	    public void sendSms( @RequestBody NotificationBean smsRequest) {

	    }
	    
	    @PutMapping("/compte/updateSold/{idAgent}")
		public ResponseEntity<CompteBean> updateSol(@PathVariable("idAgent") Long idAgent,@RequestParam(required = true) float solde){
	    	return microserviceCompteProxy.updateSol(idAgent, solde);
	}
	    @GetMapping("/transfert/find/{codetransfert}")
		public ResponseEntity<TransfertBean> getTransfertByCodeTransfert(@PathVariable("codetransfert") String codeTransfert){
	    	return microserviceTransfertProxy.getTransfertByCodeTransfert(codeTransfert);
	    
	    }
	    @PutMapping("/transfert/status/{codeTransfert}")
		public ResponseEntity<TransfertBean> updateTransfert(@PathVariable("codeTransfert") String codeTransfert,@RequestParam(required = true) String status,@RequestParam(required = true) String motif){
	    	return microserviceTransfertProxy.updateTransfert(codeTransfert, status, motif);
	    }
		
}
