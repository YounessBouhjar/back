package com.example.agent.controller;

import com.example.agent.beans.CompteBean;
import com.example.agent.exception.AgentNotFoundException;
import com.example.agent.model.Agent;
import com.example.agent.proxies.MicroserviceCompteProxy;
import com.example.agent.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent")
@CrossOrigin

public class AgentController {
    private final AgentService agentService;
    @Autowired
    MicroserviceCompteProxy microserviceCompteProxy;

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
    @PutMapping("/update/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable Long id ,@RequestBody Agent agent) throws Exception {
        Agent updateAgent = agentService.updateAgent(id,agent);
        return new ResponseEntity<Agent>(updateAgent, HttpStatus.OK);
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


}
