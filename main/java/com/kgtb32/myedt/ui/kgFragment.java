package com.kgtb32.myedt.ui;

import android.app.Activity;
import android.content.Context;

import com.kgtb32.icallib.Event;
import com.kgtb32.myedt.model.PreLoadingDialog;

import java.util.ArrayList;

public interface kgFragment {
    void notifyDownloadError();
    ArrayList<Event> getEvents();
    void setEvents(Event[] events);
    void updateList();
    void updateList(ArrayList<Event> events);

    Activity getAct();
    PreLoadingDialog getPreLoadingDialog();

    public Context getCont();

    public boolean isFirstStart();
    public void setIsFirstStart(boolean newBool);
}
