package com.kgtb32.icallib;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeHelper {

    public static String toIcalTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(date);
    }

    /**
     * Méthode icalTimeParser(
     * @param time : String >> Date récupéré à partir du fichier Ical /!\ Cette date doit être transcodée en UTC à cause des fuseaux horaires.
     *             )
     *  @return Date
     *  Cette méthode permet de transformer une date ICS sous forme de chaîne de caractères et la transcode en Date en appliquant le fuseau horaire du système.
     */

    public static Date icalTimeParser(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'"); //Définition du format de stockage de la date : Format Ical YYYYMMdd'T'HHmmss'Z'
        format.setTimeZone(TimeZone.getTimeZone("UTC")); //application du fuseau horaire actuel
        Date date;
        try {
             date = format.parse(time); //on parse la date
            return date;
        } catch (ParseException e) {
            try{
                format = new SimpleDateFormat("yyyyMMdd");
                date = format.parse(time);
                return date;
            }
            catch(ParseException e2){
                Log.e("[TimeHelper.class]","une date n'a pas pu être parsée correctement, oubli ...");
                e2.printStackTrace();
            }
            //e.printStackTrace();
        }
        return new Date(); //si erreur, on retourne une date vide
    }
}
