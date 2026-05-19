package com.example.smilecaremobile.modeles;

public class Service {
    private int id_service;
    private String name;
    private String description;
    private int duree;
    private String categorie;

    //Constructeur
    public Service(int id_service, String name, String description, int duree, String categorie){
        this.id_service = id_service;
        this.name = name;
        this.description = description;
        this.duree = duree;
        this.categorie = categorie;
    }

    //Getters et Setters
    public int getId_service() {
        return id_service;
    }
    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuree() {
        return duree;
    }
    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
