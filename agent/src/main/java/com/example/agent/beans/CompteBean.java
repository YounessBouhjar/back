package com.example.agent.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompteBean {
    private Long id;
    private String numCompte;
    private float solde;
    private String nomClient;
    private String typeCompte;
    private LocalDateTime createAt;
    private Long idClient;

}
