package com.kgtb32.myedt.model;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kgtb32.myedt.R;
import com.kgtb32.myedt.model.database.RappelsDatabaseHelper;
import com.kgtb32.myedt.ui.home.HomeFragment;

import java.util.ArrayList;

public class Dialog_rappel {

    private HomeFragment activity;

    private AlertDialog dialog;

    private ListView list;

    private void désérialiser(View root){
        this.list = root.findViewById(R.id.dialog_rappel_list);
        ArrayList<Rappel> rappels = new RappelsDatabaseHelper(this.activity.getContext()).getFromBD();
        this.list.setAdapter(new RappelsListAdapter(this.activity.getContext(),R.layout.adapter_rappel,rappels));
        if(rappels.size() == 0){
            this.list.setVisibility(View.GONE);
            root.findViewById(R.id.dialog_rappels_noRap).setVisibility(View.VISIBLE);
        }

    }

    public Dialog_rappel(HomeFragment activity){
        this.activity = activity;
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.activity.getActivity(),0); //création de la boite de dialogue
        final View mView = this.activity.getLayoutInflater().inflate(R.layout.dialog_rappels,null); //cette boite de dialogue, utilisera le layout dialog_color_select
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
