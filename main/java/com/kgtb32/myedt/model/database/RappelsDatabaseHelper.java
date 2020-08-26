package com.kgtb32.myedt.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.kgtb32.icallib.Event;
import com.kgtb32.icallib.TimeHelper;
import com.kgtb32.myedt.model.Rappel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RappelsDatabaseHelper extends SQLiteOpenHelper {
    public RappelsDatabaseHelper(Context context){
        super(context, RappelsDatabaseConstantes.DATABASE_NAME,null,RappelsDatabaseConstantes.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RappelsDatabaseConstantes.SCRIPT_CREATE);
    }

    public long addToBD(Rappel r){
        this.getWritableDatabase().beginTransaction();
        ContentValues values = new ContentValues();
        values.put("nom",r.getNom());
        values.put("dateExec", TimeHelper.toIcalTime(r.getDate()));
        values.put("eventJSON",r.getEvent().toJSON());
        long id2 = this.getWritableDatabase().insert("rappels", null, values);
        this.getWritableDatabase().delete("rappels","id="+id2,null);
        Log.e("id",this.getWritableDatabase().insert("rappels", null, values)+"");
        this.getWritableDatabase().setTransactionSuccessful();
        this.getWritableDatabase().endTransaction();
        this.getWritableDatabase().close();
        super.close();
        return 0;
    }

    public ArrayList<Rappel> getFromBD(){
        this.getWritableDatabase().beginTransaction();
        Date dateNow = Calendar.getInstance().getTime();
        Log.e("date :",dateNow.toString());
        ArrayList<Integer> toDestroyID = new ArrayList<>();
        ArrayList<Rappel> rappels = new ArrayList<>();
        Cursor c = this.getWritableDatabase().rawQuery("SELECT * FROM rappels ", null);
        try {
            c.moveToFirst();
            if (c.getCount() <= 0) {
                return rappels;
            }
            while (!c.isLast()) {
                Rappel r = new Rappel(c.getInt(0), c.getString(1), c.getString(2), new Gson().fromJson(c.getString(3), Event.class));
                Log.v("i", "id: " + c.getLong(0) + " | " +  r.getDate().compareTo(Calendar.getInstance().getTime()) + " | " + r.getDate() + " | " + c.getString(2) + " | " + r.getNom());
                if (r.getDate().compareTo(dateNow) < 0) {
                    Log.v("DBHelper", "une entrée de la base de données à expirée, supression ...");
                    toDestroyID.add(r.getId());
                } else {
                    Log.e("i", "added");
                    rappels.add(r);
                }
                c.moveToNext();
            }
            //TODO destructions des ID Expirés
            Log.e("i", rappels.size() + "");
        }
        finally {
            c.close();
        }
        this.getWritableDatabase().endTransaction();
        super.close();

        return rappels;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop table
        db.execSQL(RappelsDatabaseConstantes.SCRIPT_ONUPGRADE);
        // Recreate
        onCreate(db);
    }

}

