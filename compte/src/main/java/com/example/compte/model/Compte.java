package com.example.compte.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compte {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numCompte;
    private float solde;
    private String nomClient;
    private String typeCompte;
    private LocalDateTime createAt;
    private Long idClient;
    private Long idAgent;
    private Long idAdmin;
}
