package com.kgtb32.myedt.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kgtb32.icallib.Event;
import com.kgtb32.myedt.R;

import java.util.ArrayList;

public class RappelsListAdapter extends ArrayAdapter<Rappel> {
    private static final String tab =  "EventListAdapter";

    private Context contexte;
    private int ressource;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.contexte);
        convertView = inflater.inflate(this.ressource,parent,false);


        //désérialisation
        TextView Nom = convertView.findViewById(R.id.adapter_rappel_title);
        TextView DateExec = convertView.findViewById(R.id.adapter_rappel_exec);

        Nom.setText(super.getItem(position).getNom());
        DateExec.setText(super.getItem(position).getDate().toString());

        return convertView;
    }

    public RappelsListAdapter(Context contexte, int layout, ArrayList<Rappel> Rappel){
        super(contexte,layout,Rappel);
        this.contexte = contexte;
        this.ressource = layout;
    }
}
