package com.example.smilecaremobile.modeles;

public class Paiement {
    private int id_paiement;
    private float montant;
    private String date;
    private String etat;
    private String type;

    //Constructeur
    public Paiement(int id_paiement, float montant, String date, String etat, String type){
        this.id_paiement = id_paiement;
        this.montant = montant;
        this.date = date;
        this.etat = etat;
        this.type = type;
    }

    //Getters et Setters
    public int getId_paiement() {
        return id_paiement;
    }
    public void setId_paiement(int id_paiement) {
        this.id_paiement = id_paiement;
    }

    public float getMontant() {
        return montant;
    }
    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }
    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getType() {
        return type;
    }
    public void seTtype(String type) {
        this.type = type;
    }
}
