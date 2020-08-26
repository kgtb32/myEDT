package com.kgtb32.myedt.model;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.kgtb32.myedt.R;
import com.kgtb32.myedt.ui.home.HomeFragment;

import java.util.Calendar;
import java.util.Date;

public class DateSelectDialog {
    private HomeFragment activity;
    private Date selectedDate;

    private Button btnClose;
    private Button btnValid;

    private DatePicker datePicker;

    private AlertDialog dialog;

    private void addActions(){
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateSelectDialog.this.dialog.dismiss();
            }
        });

        this.btnValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance(); // récupération du calendrier
                cal.set(DateSelectDialog.this.datePicker.getYear(), DateSelectDialog.this.datePicker.getMonth(), DateSelectDialog.this.datePicker.getDayOfMonth()); //définition de la date du calendrier à la date séléctionnée dans le calendrier
                DateSelectDialog.this.selectedDate = cal.getTime(); //conversion Calender > Date puis stockage en variable d'instance
                DateSelectDialog.this.activity.setDate(cal.getTime());
                DateSelectDialog.this.activity.getEdtHelper().setDate(DateSelectDialog.this.selectedDate); //on définit la date de l'objet EDTHelper à la date modifiée
                DateSelectDialog.this.activity.getEdtHelper().execute(); //on execute la demande de téléchargement et d'affichage
                dialog.dismiss(); //on ferme la boite de dialogue
            }
        });
    }

    private void désérialiser(View vue){
        this.btnValid = vue.findViewById(R.id.dialogChooseDateValidatebtn);
        this.btnClose = vue.findViewById(R.id.dialogChooseDateClose);
        this.datePicker = vue.findViewById(R.id.dateAdapter);
        this.addActions();
    }

    public DateSelectDialog(HomeFragment activity) {
        this.activity = activity;
        this.selectedDate = Calendar.getInstance().getTime();
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.activity.getActivity(),0); //création de la boite de dialogue
        final View mView = this.activity.getLayoutInflater().inflate(R.layout.dialog_choosedate,null); //cette boite de dialogue, utilisera le layout dialog_choosedate
        this.désérialiser(mView); //désérialisation des composants de la boite de dialogue
        mBuilder.setView(mView); //on définit la vue.
        this.dialog = mBuilder.create(); //création de l'objet de la boite de dialogue (pas encore affiché)
    }

    public void afficher(){
        this.dialog.show();
    }

    public Date getSelectedDate(){
        return this.selectedDate;
    }


}
