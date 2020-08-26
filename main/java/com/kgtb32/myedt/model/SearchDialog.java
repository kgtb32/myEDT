package com.kgtb32.myedt.model;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;

import com.kgtb32.myedt.R;
import com.kgtb32.myedt.model.enums.EnumTypeSearch;
import com.kgtb32.myedt.ui.home.HomeFragment;

public class SearchDialog {

    private Button btnByTT;
    private Button btnByE;

    private EnumTypeSearch searchMethod;
    private AlertDialog dialog;

    private HomeFragment activity;


    private void addActions(){
        this.btnByTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDialog.this.searchMethod = EnumTypeSearch.ByTitle;
                SearchDialog.this.dialog.dismiss();
            }
        });
        this.btnByE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDialog.this.searchMethod = EnumTypeSearch.ByEverything;
                SearchDialog.this.dialog.dismiss();
            }
        });
    }

    private void désérialiser(View root){
        this.btnByE = root.findViewById(R.id.searchDialog_btnByE);
        this.btnByTT = root.findViewById(R.id.searchDialog_btnTT);
        this.addActions();
    }

    public SearchDialog(HomeFragment activity){
        this.activity = activity;
        this.searchMethod = EnumTypeSearch.ByTitle;
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.activity.getActivity(),0); //création de la boite de dialogue
        final View mView = this.activity.getLayoutInflater().inflate(R.layout.dialog_searchoptions,null); //cette boite de dialogue, utilisera le layout dialogSearchOptions
        this.désérialiser(mView); //désérialisation des composants de la boite de dialogue
        mBuilder.setView(mView); //on définit la vue.
        this.dialog = mBuilder.create(); //création de l'objet de la boite de dialogue (pas encore affiché)
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public EnumTypeSearch getSearchMethod(){
        return this.searchMethod;
    }

    public void show(){
        this.dialog.show();
    }

    public void close(){
        this.dialog.dismiss();
    }

}
