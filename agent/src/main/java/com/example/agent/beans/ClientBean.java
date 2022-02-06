package com.example.agent.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientBean {
	private Long clientId;
    private String prenom;
    private String nom;
    private String cin;
    private String numGSM;
    private String titre;
    private String typePI;
    private String paysemission;
    private Date validiteId;
    private Date dateNaissance;
    private String profession;
    private String nationalite;
    private String paysAdress;
    private String ville;
    private String adress;
    private String email;
    private String password;
    private String role;

}
