package com.kgtb32.myedt.model;

import android.app.AlertDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kgtb32.myedt.R;
import com.kgtb32.myedt.ui.settings.SettingsFragment;


public class Dialog_Color_Select implements View.OnClickListener {
    private SettingsFragment activity;
    private AlertDialog dialog;

    private FloatingActionButton[] btns;
    private int[] themeID;
    private String[] themeName;


    private void désérialiser(View root){
        this.themeID = ThemeHelper.generateThemeIDArray();
        this.themeName = ThemeHelper.generateThemeNameArray(root);
        this.btns = new FloatingActionButton[this.themeID.length];

        int actualThemeID = this.activity.getAct().getSettingsHelper().getThemeId();
        for(int i=0;i<this.themeID.length;i++){
            String btnID = "dialog_color_btn_"+i;
            int resID = this.activity.getResources().getIdentifier(btnID, "id", this.activity.getAct().getPackageName());
            this.btns[i] = root.findViewById(resID);
            if(actualThemeID == this.themeID[i]){
                this.btns[i].setImageDrawable(ContextCompat.getDrawable(root.getContext(), R.drawable.ic_check_black_24dp));
            }
            this.btns[i].setOnClickListener(this);
        }

    }

    public Dialog_Color_Select(SettingsFragment activity){
        this.activity = activity;
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.activity.getActivity(),0); //création de la boite de dialogue
        final View mView = this.activity.getLayoutInflater().inflate(R.layout.dialog_colors,null); //cette boite de dialogue, utilisera le layout dialog_color_select
        this.désérialiser(mView); //désérialisation des composants de la boite de dialogue
        mBuilder.setView(mView); //on définit la vue.
        this.dialog = mBuilder.create(); //création de l'objet de la boite de dialogue (pas encore affiché)
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show(){
        this.dialog.show();
    }

    private int getIDIndex(int id){
        for(int i=0;i<this.btns.length;i++){
            if(this.btns[i].getId() == id){
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        int clickedID  = v.getId();
        int pos = Dialog_Color_Select.this.getIDIndex(clickedID);
        this.activity.setTheme(this.themeName[pos],this.themeID[pos]);
        this.dialog.dismiss();
    }
}
