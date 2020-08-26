package com.kgtb32.myedt.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context){
        super(context, FilterDatabaseConstantes.DATABASE_NAME,null,FilterDatabaseConstantes.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FilterDatabaseConstantes.SCRIPT_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop table
        db.execSQL(FilterDatabaseConstantes.SCRIPT_ONUPGRADE);
        // Recreate
        onCreate(db);
    }
}
