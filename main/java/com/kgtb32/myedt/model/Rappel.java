package com.kgtb32.myedt.model;

import com.google.gson.Gson;
import com.kgtb32.icallib.Event;
import com.kgtb32.icallib.TimeHelper;

import java.util.Date;

public class Rappel {
    private int id;
    private String nom;
    private Date date;
    private Event event;

    public Rappel(int id, String nom, String date, Event event){
        this(nom,date,event);
        this.id = id;

    }

    public Rappel(String nom, String date, Event event){
        this.nom = nom;
        this.date = TimeHelper.icalTimeParser(date);
        this.event = event;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
