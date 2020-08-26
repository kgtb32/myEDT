package com.kgtb32.myedt.model;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgtb32.icallib.Event;
import com.kgtb32.icallib.TimeHelper;
import com.kgtb32.myedt.R;
import com.kgtb32.myedt.model.database.RappelsDatabaseHelper;
import com.kgtb32.myedt.ui.home.HomeFragment;

import java.util.Calendar;
import java.util.Date;

public class dialog_addRappel {

    private HomeFragment activity;

    private Event event;

    private AlertDialog dialog;

    private Spinner dateRappel;

    private EditText nomField;

    private FloatingActionButton btnNext;
    private FloatingActionButton btnClose;

    private void addActions(){
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_addRappel.this.close();
            }
        });

        this.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dialog_addRappel.this.nomField.getText().toString().length() == 0){
                    dialog_addRappel.this.nomField.setError("le nom est obligatoire");
                }
                else{
                    int els[]= {-1,-2,-4,-24,-48,0};

                    Date date = dialog_addRappel.this.event.getDateDebut();
                    Calendar.getInstance().setTime(date);
                    //Calendar.getInstance().add(Calendar.HOUR,els[0]);//dialog_addRappel.this.dateRappel.getSelectedItemPosition()

                    long id = new RappelsDatabaseHelper(
                            dialog_addRappel.this.activity.getContext()
                    ).addToBD(new Rappel(
                            dialog_addRappel.this.nomField.getText().toString(),
                            TimeHelper.toIcalTime(Calendar.getInstance().getTime()
                            ),
                            dialog_addRappel.this.event
                    ));
                    dialog_addRappel.this.setNotification(date,id);
                    dialog_addRappel.this.close();
                    Log.e("i","added");

                }
            }
        });
    }

    private void setNotification(Date time, long id){
        Intent notifyIntent = new Intent(this.activity.getContext(), NotificationReceiver.class);
        notifyIntent.putExtra("data",this.event.toJSON());
        notifyIntent.putExtra("id",id);
        PendingIntent  pint =  PendingIntent.getBroadcast(this.activity.getContext(), 1, notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT );
        AlarmManager alarmManager = (AlarmManager) this.activity.getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,time.getTime()-Calendar.getInstance().getTime().getTime(),pint);

    }

    private void désérialiser(View root){
        this.nomField = root.findViewById(R.id.dialog_addRappel_nom);
        this.btnClose = root.findViewById(R.id.dialog_addRappel_btnClose);
        this.btnNext = root.findViewById(R.id.dialog_addRappel_btnNext);
        this.nomField.setBackground(new ColorDrawable(Color.TRANSPARENT));
        this.addActions();
    }

    public dialog_addRappel(HomeFragment activity, Event event){
        this.activity = activity;
        this.event = event;
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.activity.getActivity(),0); //création de la boite de dialogue
        final View mView = this.activity.getLayoutInflater().inflate(R.layout.dialog_addrappel,null); //cette boite de dialogue, utilisera le layout dialog_detailEvent
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
}
