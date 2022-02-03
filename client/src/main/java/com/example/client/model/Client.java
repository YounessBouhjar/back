package com.example.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long clientId;
    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
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
