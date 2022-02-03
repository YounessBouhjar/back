package com.example.beneficiaire.service;

import com.example.beneficiaire.exception.BeneficiaireDuplicatedException;
import com.example.beneficiaire.exception.BeneficiaireNotFoundException;
import com.example.beneficiaire.model.Beneficiaire;
import com.example.beneficiaire.repository.BeneficiaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaireService {
    @Autowired
    private final BeneficiaireRepository beneficiaireRepository;

    public BeneficiaireService(BeneficiaireRepository beneficiaireRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
    }

    /////add beneficiaire
    public Beneficiaire addBeneficiaire(Beneficiaire beneficiaire) throws BeneficiaireDuplicatedException {
        Beneficiaire beneficiaireFromDB = beneficiaireRepository.findById(beneficiaire.getId()).orElse(null);
        if (beneficiaireFromDB != null)
            throw new BeneficiaireDuplicatedException(beneficiaire.getId());
        return beneficiaireRepository.save(beneficiaire);
    }

    ////// find all beneficiaire
    public List<Beneficiaire> findAllBeneficiaires(){
        return beneficiaireRepository.findAll();
    }

    /////update
    public Beneficiaire updateBeneficiaire(Beneficiaire beneficiaire) throws BeneficiaireNotFoundException {
        Beneficiaire movieFromDB = beneficiaireRepository.findById(beneficiaire.getId()).orElse(null);
        if (movieFromDB == null)
            throw new BeneficiaireNotFoundException("aucun beneficiaire avec cette id");
        beneficiaire.setId(movieFromDB.getId());
        return beneficiaireRepository.save(beneficiaire);
    }

    ////delete
    public Long deleteBeneficiaire(Long id) throws BeneficiaireNotFoundException {
        Beneficiaire movieFromDB = beneficiaireRepository.findById(id).orElse(null);
        if (movieFromDB == null)
            throw new BeneficiaireNotFoundException("aucun beneficiaire");
        beneficiaireRepository.delete(movieFromDB);
        return id;
    }

    /////find beneficiaire by id
    public Beneficiaire findBeneficiaireById(Long id){
        return beneficiaireRepository.findBeneficiaireById(id);
    }
}
