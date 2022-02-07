package com.example.agent.beans;

import java.util.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaireBean {
    private Long id;
    private String nom;
    private String prenom;
    private String numGSM;
}
