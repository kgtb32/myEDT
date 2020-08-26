package com.kgtb32.myedt.model;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.kgtb32.myedt.R;
import com.kgtb32.myedt.ui.home.HomeFragment;


public class DialogNETError {

    private HomeFragment activity;

    private Button btnClose;

    private AlertDialog dialog;

    private void addActions(){
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogNETError.this.dialog.dismiss();
            }
        });
    }

    private void désérialiser(View root){
        this.btnClose = root.findViewById(R.id.dialog_net_error_close_btn);
        this.addActions();
    }

    public DialogNETError(HomeFragment activity){
        this.activity = activity;
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.activity.getActivity(),0); //création de la boite de dialogue
        final View mView = this.activity.getLayoutInflater().inflate(R.layout.dialog_net_error,null); //cette boite de dialogue, utilisera le layout dialog_net_error
        this.désérialiser(mView); //désérialisation des composants de la boite de dialogue
        mBuilder.setView(mView); //on définit la vue.
        this.dialog = mBuilder.create(); //création de l'objet de la boite de dialogue (pas encore affiché)
    }

    public void show(){
        this.dialog.show();
    }
}
