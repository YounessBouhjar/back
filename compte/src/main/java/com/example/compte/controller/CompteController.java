package com.example.compte.controller;

import com.example.compte.exception.NotFoundException;
import com.example.compte.model.Compte;
import com.example.compte.service.CompteService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compte")
public class CompteController {
	@Autowired
    private final CompteService compteService;
	@Autowired

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Compte>> getAllCompte () {
        List<Compte>comptes = compteService.findAllComptes();
        return new ResponseEntity<>(comptes, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Compte> addCompte(@RequestBody Compte compte) throws Exception{
        Compte compte2 = compteService.addCompte(compte);
        return new ResponseEntity<>(compte2, HttpStatus.CREATED);
    }
    @GetMapping("/find/{numCompte}")
    public ResponseEntity<Compte> findCompteBynumCompte(@PathVariable("numCompte") String numCompte){
        Compte compte=compteService.getCompteByNumero(numCompte);
        return new ResponseEntity<>(compte,HttpStatus.OK);
    }
    @GetMapping("/find/client/{nomClient}")
    public ResponseEntity<Compte> findCompteByNomClient(@PathVariable("nomClient") String nomClient){
        Compte compte=compteService.getCompteByNumero(nomClient);
        return new ResponseEntity<>(compte,HttpStatus.OK);
    }

    //debiter le compte
    @PutMapping("/debiter/{id}/{solde}")
    public ResponseEntity<Compte> updateCompte(@PathVariable("id") Long id,@PathVariable("solde") float solde){
       Compte compte= compteService.debiterCompte(id,solde);
        return new ResponseEntity<>(compte,HttpStatus.OK);
    }
    //crediter le compte
    @PutMapping("/crediter/{id}/{solde}")
    public ResponseEntity<Compte> crediterCompte(@PathVariable("id") Long id,@PathVariable("solde") float solde){
        Compte compte= compteService.crediterCompte(id,solde);
        return new ResponseEntity<>(compte,HttpStatus.OK);
    }


    @GetMapping("/getBackoffice")
    public ResponseEntity<Compte> findCompteByNom(){
        Compte compte=compteService.findCompteByNom("backoffice");
        return new ResponseEntity<>(compte,HttpStatus.OK);
    }
    
    @PutMapping("/updateSolde/{nomClient}")
	public ResponseEntity<Compte> updateSolde(@PathVariable("nomClient") String nomClient,@RequestParam(required = true) float solde){
    	Compte compte=compteService.updateAccount(nomClient, solde);
  return new ResponseEntity<>(compte, HttpStatus.OK);

}
    @GetMapping("/findClient/{idClient}")
    public ResponseEntity<List<Compte>> getCompteByIdClient(@PathVariable("idClient") Long idClient){
        List<Compte> compte=compteService.findClientByIdClient(idClient);
        return new ResponseEntity<>(compte,HttpStatus.OK);
    }

    @GetMapping("/findAgent/{idAgent}")
    public ResponseEntity<Compte> getCompteByIdAgent(@PathVariable("idAgent") Long idAgent){
        Compte compte=compteService.findClientByIdAgent(idAgent);
        return new ResponseEntity<>(compte,HttpStatus.OK);
    }
    @GetMapping("/findAdmin/{idAdmin}")
    public ResponseEntity<Compte> getCompteByIdAdmin(@PathVariable("idAdmin") Long idAdmin){
        Compte compte=compteService.findClientByIdAdmin(idAdmin);
        return new ResponseEntity<>(compte,HttpStatus.OK);
    }
    @PutMapping("/updateSold/{idAgent}")
   	public ResponseEntity<Compte> updateSol(@PathVariable("idAgent") Long idAgent,@RequestParam(required = true) float solde){
       	Compte compte=compteService.updateAcc(idAgent, solde);
     return new ResponseEntity<>(compte, HttpStatus.OK);

   }
}