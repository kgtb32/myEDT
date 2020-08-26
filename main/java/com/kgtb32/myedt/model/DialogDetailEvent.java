package com.kgtb32.myedt.model;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgtb32.icallib.Event;
import com.kgtb32.icallib.EventDescription;
import com.kgtb32.myedt.R;
import com.kgtb32.myedt.ui.home.HomeFragment;

import java.util.ArrayList;

public class DialogDetailEvent {

    private HomeFragment activity;

    private FloatingActionButton btnClose;
    private FloatingActionButton btnAddRappel;

    private TextView Summary;
    private TextView dateDeb;
    private TextView dateFin;
    private TextView Data;

    private Event event;

    private AlertDialog dialog;

    private void addActions(){
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDetailEvent.this.close();
            }
        });
        this.btnAddRappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDetailEvent.this.close();
                new dialog_addRappel(DialogDetailEvent.this.activity,DialogDetailEvent.this.event).show();
            }
        });
    }

    private void désérialiser(View root){
        this.Summary = root.findViewById(R.id.dialog_detailevent_summary);
        this.dateDeb = root.findViewById(R.id.dialog_detailevent_dateDeb);
        this.dateFin = root.findViewById(R.id.dialog_detailevent_dateFin);
        this.Data = root.findViewById(R.id.dialog_detailevent_Data);
        this.btnClose = root.findViewById(R.id.dialog_detailEvent_btnClose);
        this.btnAddRappel = root.findViewById(R.id.dialog_detailEvent_btnAddRappel);
        this.addActions();
        this.updateInfos(root);
    }

    private void updateInfos(View root){
        this.Summary.setText(event.getSummary());
        this.dateDeb.setText(event.getDateDebut().toString());
        this.dateFin.setText(event.getDateFin().toString());
        this.Data.setText(this.ArrayListToUl(event.getEventDescription(),root));

    }

    public DialogDetailEvent(HomeFragment activity, Event event){
        this.activity = activity;
        this.event = event;
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.activity.getActivity(),0); //création de la boite de dialogue
        final View mView = this.activity.getLayoutInflater().inflate(R.layout.dialog_detailevent,null); //cette boite de dialogue, utilisera le layout dialog_detailEvent
        this.désérialiser(mView); //désérialisation des composants de la boite de dialogue
        mBuilder.setView(mView); //on définit la vue.
        this.dialog = mBuilder.create(); //création de l'objet de la boite de dialogue (pas encore affiché)
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show(){
        this.dialog.show();
    }

    public void close(){
        this.dialog.dismiss();
    }

    private String ArrayListToUl(ArrayList<EventDescription> e, View convertView){
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
