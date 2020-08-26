package com.kgtb32.icallib;

public class EventDescription {

    private String nom;
    private String valeur;
    private int nomID;

    public EventDescription(String nom,String valeur){
        this.nomID = NameTranslateHelper.translateName(nom);
        this.nom = nom;
        this.valeur = valeur;
    }

    public String getNom() {
        return nom;
    }

    public int getNomID(){
        return this.nomID;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

}
