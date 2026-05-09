/****************************************
 Fichier : Utilisateur.java
 Auteur : Abdoulaye Dembele
 Fonctionnalité : MGC01 — Modèle de données Utilisateur
 Date : 2026-05-09
 Vérification :
 Date        Nom         Approuvé
 =========================================================
 Historique de modifications :
 Date        Nom         Description
 =========================================================
 ****************************************/

package com.example.smilecaremobile;

import java.util.Date;

/**
 * Modèle représentant un utilisateur/client du système SmileCare.
 * Correspond à la table Utilisateur de la base de données locale SQLite.
 */
public class Utilisateur {
    private int id_utilisateur;
    private String nom;
    private String prenom;
    private String photo;
    private Date date_naissance;
    private String adresse;
    private String telephone;
    private String mdp;
    private String num_assurance;
    private int id_role;

    // Constructeurs, getters et setters
    public Utilisateur(int id_utilisateur, String nom, String prenom, String photo, Date date_naissance, String adresse, String telephone, String mdp, String num_assurance, int id_role) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.photo = photo;
        this.date_naissance = date_naissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.mdp = mdp;
        this.num_assurance = num_assurance;
        this.id_role = id_role;
    }

    // Getters et setters


    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNum_assurance() {
        return num_assurance;
    }

    public void setNum_assurance(String num_assurance) {
        this.num_assurance = num_assurance;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }
}
