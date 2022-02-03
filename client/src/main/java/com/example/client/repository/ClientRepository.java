package com.example.client.repository;

import com.example.client.model.Client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
     Client findBynumGSM(String numGSM);
     Client findBycin(String cin);
     Client findByClientId(Long id);
     Client findClientByEmail(String email);

     }
