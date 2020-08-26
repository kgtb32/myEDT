package com.kgtb32.myedt.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kgtb32.icallib.Event;
import com.kgtb32.icallib.EventDescription;
import com.kgtb32.myedt.R;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<Event> {
    private static final String tab =  "EventListAdapter";

    private Context contexte;
    private int ressource;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.contexte);
        convertView = inflater.inflate(this.ressource,parent,false);

        String DateDebut = this.getItem(position).getDateDebut().toString();
        String DateFin = this.getItem(position).getDateFin().toString();
        String Summary = this.getItem(position).getSummary();
        String descText = EventListAdapter.ArrayListToUl(this.getItem(position).getEventDescription(),convertView);


        TextView viewTitle = (TextView) convertView.findViewById(R.id.adapter_title);
        TextView viewDateDebut = (TextView) convertView.findViewById(R.id.adapter_date_debut);
        TextView viewDateFin = (TextView) convertView.findViewById(R.id.adapter_date_fin);
        TextView viewDesc = (TextView) convertView.findViewById(R.id.adapter_desc);

        viewTitle.setText(Summary);
        viewDateDebut.setText(DateDebut);
        viewDateFin.setText(DateFin);
        viewDesc.setText(descText);
        return convertView;
    }

    public EventListAdapter(Context contexte, int layout, ArrayList<Event> events){
        super(contexte,layout,events);
        this.contexte = contexte;
        this.ressource = layout;
    }

    public static String ArrayListToUl(ArrayList<EventDescription> e,View convertView){
        String str = new String();
        for(int i=0;i<e.size();i++){
            String nom;
            if(e.get(i).getNomID() == -1){
                 nom = e.get(i).getNom();
            }
            else{
                nom = convertView.getResources().getString(e.get(i).getNomID());
            }

            str+="\u23FA "+nom+" : "+e.get(i).getValeur()+"\n";
        }
        return str;
    }
}
