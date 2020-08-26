package com.kgtb32.myedt.model;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kgtb32.myedt.R;
import com.kgtb32.myedt.ui.settings.SettingsFragment;

public class Dialog_URL_Edit {
    private SettingsFragment activity;

    private EditText urlField;

    private Button btnClose;

    private AlertDialog dialog;

    private void addActions(){
        this.urlField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Dialog_URL_Edit.this.urlField.getText().length() == 0){
                    Dialog_URL_Edit.this.urlField.setTextColor(Color.RED);
                    Dialog_URL_Edit.this.urlField.setError("L'URL doit contenir des caractères.");
                }
                else if((!Dialog_URL_Edit.this.urlField.getText().toString().contains("http://")) && (!Dialog_URL_Edit.this.urlField.getText().toString().contains("https://"))){
                    Dialog_URL_Edit.this.urlField.setTextColor(Color.RED);
                    Dialog_URL_Edit.this.urlField.setError("L'URL doit commencer par http:// ou https:// !");
                }
                else{
                    Dialog_URL_Edit.this.urlField.setTextColor(Color.GREEN);
                    Dialog_URL_Edit.this.urlField.setError(null);
                }

            }
        });
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Dialog_URL_Edit.this.urlField.getText().length() == 0){
                    Dialog_URL_Edit.this.urlField.setTextColor(Color.RED);
                    Dialog_URL_Edit.this.urlField.setError("L'URL doit contenir des caractères.");
                }
                else if((!Dialog_URL_Edit.this.urlField.getText().toString().contains("http://")) && (!Dialog_URL_Edit.this.urlField.getText().toString().contains("https://"))){
                    Dialog_URL_Edit.this.urlField.setTextColor(Color.RED);
                    Dialog_URL_Edit.this.urlField.setError("L'URL doit commencer par http:// ou https:// !");
                }
                else{
                    Dialog_URL_Edit.this.activity.getAct().getSettingsHelper().setURL(Dialog_URL_Edit.this.urlField.getText().toString());
                    Dialog_URL_Edit.this.activity.getAct().getStorageHelper().saveSettingsData(Dialog_URL_Edit.this.activity.getAct().getSettingsHelper());
                    Dialog_URL_Edit.this.dialog.dismiss();
                }

            }
        });
    }

    private void désérialiser(View root){
        this.urlField = root.findViewById(R.id.dialog_URL_field);
        this.btnClose = root.findViewById(R.id.dialog_URLbtnValidate);
        this.addActions();
    }

    public Dialog_URL_Edit(SettingsFragment frag){
        this.activity = frag;
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.activity.getActivity(),0); //création de la boite de dialogue
        final View mView = this.activity.getLayoutInflater().inflate(R.layout.dialog_url,null); //cette boite de dialogue, utilisera le layout dialog_net_error
        this.désérialiser(mView); //désérialisation des composants de la boite de dialogue
        mBuilder.setView(mView); //on définit la vue.
        this.dialog = mBuilder.create(); //création de l'objet de la boite de dialogue (pas encore affiché)
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show(){
        this.dialog.show();
    }
}
