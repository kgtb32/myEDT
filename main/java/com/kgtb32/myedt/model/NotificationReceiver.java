package com.kgtb32.myedt.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.kgtb32.icallib.Event;

public class NotificationReceiver extends BroadcastReceiver {

    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent mintent) {
        Bundle b = mintent.getExtras();
        Event event = new Gson().fromJson(b.getString("data","{}"),Event.class);
        long id = b.getLong("id",-1);
        //TODO Affichage notification
        //TODO Vérification dels ?
        //TODO DEL
        Log.e("info","rappel executé");
    }
}
