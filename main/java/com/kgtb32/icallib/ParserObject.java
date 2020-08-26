package com.kgtb32.icallib;

import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class ParserObject {
    private ArrayList<EventDescription> eventsDescs;
    private int DTSTARTPos = -1;
    private int DTENDPos = -1;
    private int SummaryPOS = -1;

    private String data;

    public ParserObject(String data){
        this.data = data;
        this.eventsDescs = new ArrayList<>();
        this.parser();
    }

    public void parser(){
        String[] data = this.data.split("\n");
        for(int i=0;i<data.length-1;i++){
            String[] strF = data[i].split(":",2);
            if(strF.length > 1){
                int index = strF[0].indexOf(";");
                if(index == -1){
                    index = strF[0].length();
                }

                String id = strF[0].substring(0,index);
                String content = strF[1];
                if(id.equals("DTSTART")){
                    this.DTSTARTPos = i;
                }
                else if (id.equals("DTEND")){
                    this.DTENDPos = i;
                }
                else if(id.equals("SUMMARY")){
                    this.SummaryPOS = i;
                }

                this.eventsDescs.add(new EventDescription(id,content));
            }

        }
    }


    public boolean isSameDate(Date date){
        if(this.DTSTARTPos == -1){
            return false;
        }
        int Yday = Integer.parseInt((String)DateFormat.format("dd",   date));
        int Ymounth = Integer.parseInt((String)DateFormat.format("MM",date));
        int Yyear = Integer.parseInt((String)DateFormat.format("yyyy",date));

        Date date2 = TimeHelper.icalTimeParser(this.eventsDescs.get(this.DTSTARTPos).getValeur());

        int Mday = Integer.parseInt((String)DateFormat.format("dd",   date2));
        int Mmounth = Integer.parseInt((String)DateFormat.format("MM",date2));
        int Myear = Integer.parseInt((String)DateFormat.format("yyyy",date2));
        if((Yday == Mday) && (Ymounth == Mmounth) && (Yyear == Myear)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){
        String DTSTART = new String();
        String DTEND = new String();
        String summary = new String();
        if(this.DTSTARTPos != -1){
            DTSTART = this.eventsDescs.get(this.DTSTARTPos).getValeur();
        }
        if(this.DTENDPos != -1){
            DTEND = this.eventsDescs.get(this.DTENDPos).getValeur();
        }
        if(this.SummaryPOS != -1){
            summary = this.eventsDescs.get(this.SummaryPOS).getValeur();
        }
        Log.e("DTSTART -",DTSTART);
        Log.e("DTEND - ",DTEND);
        Log.e("Summary -",summary);
        for(int i=0;i<this.eventsDescs.size();i++){
            Log.e(this.eventsDescs.get(i).getNom()+"  - ",this.eventsDescs.get(i).getValeur());
        }
        Log.e("END","-----------------------");
        return new String();
    }

    public void removeDouble(){
        if(this.DTSTARTPos != -1){
            this.eventsDescs.remove(this.DTSTARTPos-1);
        }
        if(this.DTENDPos != -1){
            this.eventsDescs.remove(this.DTENDPos-1);
        }
        if((this.SummaryPOS != -1) && (this.SummaryPOS < this.eventsDescs.size())){
            this.eventsDescs.remove(this.SummaryPOS-1);
        }
    }

    public String getDTSTART(){
        if(this.DTSTARTPos != -1){
            return this.eventsDescs.get(this.DTSTARTPos-1).getValeur();
        }
        else{
            return new String();
        }
    }

    public String getDTEND(){
        if(this.DTENDPos != -1){
            return this.eventsDescs.get(this.DTENDPos-1).getValeur();
        }
        else{
            return new String();
        }
    }

    public String getSummary(){
        if(this.SummaryPOS != -1){
            return  this.eventsDescs.get(this.SummaryPOS-1).getValeur();
        }
        else{
            return new String();
        }
    }

    public ArrayList<EventDescription> getEventsDescs() {
        return eventsDescs;
    }
}
