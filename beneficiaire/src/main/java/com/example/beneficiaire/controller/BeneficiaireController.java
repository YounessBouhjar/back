package com.example.beneficiaire.controller;

import com.example.beneficiaire.exception.BeneficiaireNotFoundException;
import com.example.beneficiaire.model.Beneficiaire;
import com.example.beneficiaire.service.BeneficiaireService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beneficiaire")
public class BeneficiaireController {
	@Autowired
    private final BeneficiaireService beneficiaireService;

    public BeneficiaireController(BeneficiaireService beneficiaireService) {
        this.beneficiaireService = beneficiaireService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Beneficiaire>> getAllBeneficiaires () {
        List<Beneficiaire> beneficiaires = beneficiaireService.findAllBeneficiaires();
        if(beneficiaires.isEmpty()) throw new BeneficiaireNotFoundException("aucun beneficiaire");
        return new ResponseEntity<>(beneficiaires, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Beneficiaire> addBeneficiaire(@RequestBody Beneficiaire beneficiaire) throws Exception{
        Beneficiaire newBeneficiaire = beneficiaireService.addBeneficiaire(beneficiaire);
        return new ResponseEntity<>(newBeneficiaire,HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<Beneficiaire> updateEmployee(@RequestBody Beneficiaire beneficiaire) throws Exception {
        Beneficiaire updateEmployee = beneficiaireService.updateBeneficiaire(beneficiaire);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) throws Exception{
        beneficiaireService.deleteBeneficiaire(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/findid/{id}")
    public ResponseEntity<Beneficiaire> getBeneficiaire (@PathVariable("id") Long id) {
        Beneficiaire benef = beneficiaireService.findBeneficiaireById(id);
        return new ResponseEntity<>(benef, HttpStatus.OK);
    }
}
