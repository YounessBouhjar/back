package com.example.compte.repository;

import com.example.compte.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte,Long> {
    Optional<Compte> findCompteBynumCompte(String numCompte);
    List<Compte> findCompteByNomClient(String nomClient);
    Compte findCompteById(Long id);
    Compte findCompteBynomClient(String nomClient);
    List<Compte> findCompteByIdClient(Long id);
    Compte findCompteByIdAgent(Long id);
    Compte findCompteByIdAdmin(Long id);


}
