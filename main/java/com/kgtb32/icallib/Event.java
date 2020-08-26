package com.kgtb32.icallib;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class Event {

    private Date dateDebut;
    private Date dateFin;
    private String summary;

    private ArrayList<EventDescription> eventDescription;

    public Event(String dateDebut, String dateFin, String summary, EventDescription[] descs){
        this.dateDebut = TimeHelper.icalTimeParser(dateDebut);
        this.dateFin = TimeHelper.icalTimeParser(dateFin);
        this.summary = summary;
        this.eventDescription = new ArrayList<>();
        for(int i=0;i<descs.length;i++){
            this.eventDescription.add(descs[i]);
        }
    }

    /**
     * Getters
     */

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getSummary() {
        return summary;
    }

    public ArrayList<EventDescription> getEventDescription() {
        return eventDescription;
    }

    /**
     * Setters
     */

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setEventDescription(ArrayList<EventDescription> eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String toJSON(){
        return new Gson().toJson(this);
    }
}
