package com.kgtb32.myedt.model;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import com.kgtb32.icallib.IcalParser;
import com.kgtb32.myedt.model.enums.FileType;
import com.kgtb32.myedt.ui.kgFragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class EdtHelper {
    private String URL;
    private FileType.EnumFileType fileType;
    private boolean isCacheStorageEnabled;
    private kgFragment frag;
    private Fetcher fetcher;
    private Date desiredDate;


    public EdtHelper(String URL, FileType.EnumFileType fileType, boolean isCacheStorageEnabled, kgFragment frag, Date date){
        this.URL = URL;
        this.fileType  = fileType;
        this.isCacheStorageEnabled = isCacheStorageEnabled;
        this.frag = frag;
        this.fetcher = new Fetcher(this.URL, this);
        this.desiredDate = date;
    }

    public void execute(){
        Log.e("URL",this.URL);
        Log.e("Date",this.desiredDate.toString());
        this.fetcher = new Fetcher(this.URL, this);
        if(this.fileType == FileType.EnumFileType.typeFileICS){
            Log.e("i","ICSFile");
            String tmpUR[] = this.URL.split(":",2);
            Log.e("i",this.URL);
           // this.URL = tmpUR[tmpUR.length-1];
            this.updateEdtList();
        }
        else{
            if(!this.getFrag().isFirstStart()){
                this.updateEdtList();
            }
            else{
                this.fetcher.execute();
            }

        }

    }


    public void updateEdtList(){
        Date currentTime = Calendar.getInstance().getTime();
        File file;
        if(this.fileType == FileType.EnumFileType.typeWebURL){
            file = new File(Environment.getExternalStorageDirectory()+"/.myEDT/cacheCalendar.ics");
        }
        else{
            file = new File(this.URL);
        }

        try{
            Scanner fileReader = new Scanner(file);
            IcalParser parser = new IcalParser(fileReader);
            Log.e("MyEDT [EdtHelper.class]","Parsing file");
            Log.e("edtHelper",currentTime.toString());
            if(this.desiredDate != null){
                this.frag.setEvents(parser.getDateEvent(this.desiredDate));
            }
            else{
                this.frag.setEvents(parser.getDateEvent(currentTime));
            }
            this.frag.updateList(this.frag.getEvents());
        }
        catch(IOException e){
            Log.e("MyEDT [EdtHelper.class]","IOException");
            Log.e("DIR",this.URL);
        }



    }

    public void setDate(Date newDate){
        this.desiredDate = newDate;
    }

    public void notifyError(){
        this.frag.notifyDownloadError();
    }

    public kgFragment getFrag(){
        return this.frag;
    }

}
