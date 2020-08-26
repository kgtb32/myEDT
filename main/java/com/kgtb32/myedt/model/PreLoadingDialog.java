package com.kgtb32.myedt.model;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ProgressBar;

import com.kgtb32.myedt.R;
import com.kgtb32.myedt.ui.home.HomeFragment;

public class PreLoadingDialog {
    private HomeFragment activity;

    private ProgressBar progressBar;

    private AlertDialog dialog;

    private void désérialiser(View root){
        this.progressBar = root.findViewById(R.id.dialog_pre_loading_progress);
        progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#3498db"), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public PreLoadingDialog(HomeFragment activity){
        this.activity = activity;
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.activity.getActivity(),0); //création de la boite de dialogue
        final View mView = this.activity.getLayoutInflater().inflate(R.layout.dialog_pre_loading,null); //cette boite de dialogue, utilisera le layout dialog_net_error
        this.désérialiser(mView); //désérialisation des composants de la boite de dialogue
        mBuilder.setView(mView); //on définit la vue.
        this.dialog = mBuilder.create(); //création de l'objet de la boite de dialogue (pas encore affiché)
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setProgress(int newProgress){
        this.progressBar.setProgress(newProgress);
    }

    public void resetDialog(){
        this.progressBar.setProgress(0);
    }

    public void show(){
        this.dialog.show();
    }

    public void close(){
        this.dialog.dismiss();
    }
}
