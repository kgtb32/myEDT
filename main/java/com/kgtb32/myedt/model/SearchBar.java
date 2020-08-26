package com.kgtb32.myedt.model;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kgtb32.icallib.Event;
import com.kgtb32.myedt.model.enums.EnumTypeSearch;
import com.kgtb32.myedt.ui.home.HomeFragment;

import java.util.ArrayList;

import static android.view.View.GONE;


public class SearchBar {
    private HomeFragment activity;

    private boolean needStop;
    private final Button btnOptions;
    private final Button btnClose;
    private final EditText regex;

    private SearchDialog dialog;

    private LinearLayout layout;

    private void addActions(){
        this.regex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!needStop){
                    if(SearchBar.this.regex.getText().toString().isEmpty()){
                        SearchBar.this.activity.updateList();
                    }
                    else{
                        SearchBar.this.doSearch(SearchBar.this.regex.getText().toString());
                    }
                }

            }
        });
        this.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchBar.this.dialog.show();
            }
        });
        this.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchBar.this.layout.setVisibility(GONE);
            }
        });
    }

    private void deserialiser(){
        this.addActions();
    }

    public void setNeedStop(boolean newState){
        this.needStop = newState;
    }

    public SearchBar(HomeFragment activity, Button btnClose, Button btnOptions,LinearLayout layout, EditText regex){
        this.btnClose = btnClose;
        this.btnOptions = btnOptions;
        this.activity = activity;
        this.layout = layout;
        this.regex = regex;
        this.dialog = new SearchDialog(activity);
        this.needStop = false;
        this.deserialiser();
    }

    public void doSearch(String data){
        ArrayList<Event> dataF = new ArrayList<>();
        for(int i=0;i<this.activity.getEvents().size();i++){
            boolean found = false;
            if(this.dialog.getSearchMethod() == EnumTypeSearch.ByEverything){
                for(int j=0;j<this.activity.getEvents().get(i).getEventDescription().size();j++){
                    if(this.activity.getEvents().get(i).getEventDescription().get(j).getValeur().toLowerCase().contains(data.toLowerCase())){
                        dataF.add(this.activity.getEvents().get(i));
                        found = true;
                        break;
                    }
                }
            }
            if(this.activity.getEvents().get(i).getSummary().toLowerCase().contains(data.toLowerCase()) && found == false){
                dataF.add(this.activity.getEvents().get(i));
            }
        }
        this.activity.updateList(dataF);
    }



}
