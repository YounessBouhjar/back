package com.example.agent.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransfertBean {
    private Long id;
    private Long idClient;
    private Long idAgent;
    private String pi;
    private String numGsm;
    private Long idBeneficiaire;
    private Long idCompte;
    private String status;
    private String codeTransfert;
    private String dateTransfert;
    private float montant;
    private int nombreJours;
    private String motif;
    private String motifTransfert;
    private String nomBenef;
    private String prenomBenef;

}
