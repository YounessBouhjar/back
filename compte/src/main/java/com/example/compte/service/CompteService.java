package com.example.compte.service;

import com.example.compte.exception.NotFoundException;
import com.example.compte.model.Compte;
import com.example.compte.repository.CompteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class CompteService {
    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    public Compte getCompteByNumero(String numero)
    {
        Compte compte = compteRepository.findCompteBynumCompte(numero).orElseThrow(() -> new NotFoundException("Ce compte n'existe pas"));

        return compte;
    }
    public String generateNumCompte()
    {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 57; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();
        String generatedString;
        generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> i <= 57 && i >= 48)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        try {
            getCompteByNumero(generatedString);
        }catch(NotFoundException e)
        {
            return generatedString;
        }

        return generateNumCompte();

    }
    public List<Compte> findAllComptes(){
        return compteRepository.findAll();
    }
    //ajouter compte
    public Compte addCompte(Compte compte){
        compte.setNumCompte(generateNumCompte());
        compte.setCreateAt(LocalDateTime.now());
        return compteRepository.save(compte);}
    //find compte by nom client
    public List<Compte> findCompteByNomClient(String nomClient) {
        return compteRepository.findCompteByNomClient(nomClient);
    }
    // find compte by id
    public Compte findCompteById(Long id){
        return compteRepository.findCompteById(id);
    }
    ///crediter le compte
    public Compte crediterCompte(Long id,float solde){
        Compte compte=compteRepository.findCompteById(id);
        float newSolde=compte.getSolde()-solde;
        compte.setSolde(newSolde);
        compteRepository.save(compte);
        return compte;
    }
    ///debiter le compte
    public Compte debiterCompte(Long id,float solde){
        Compte compte=compteRepository.findCompteById(id);
        float newSolde=compte.getSolde()+solde;
        compte.setSolde(newSolde);
        compteRepository.save(compte);
        return compte;
    }


}