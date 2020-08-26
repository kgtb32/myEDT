package com.kgtb32.myedt.model.database;

public class RappelsDatabaseConstantes {
    public static String DATABASE_NAME = "Rappels.db";
    public static int DATABASE_VERSION = 1;
    public static String SCRIPT_ONUPGRADE = "DROP TABLE IF EXISTS rappels;";
    public static String SCRIPT_CREATE = "CREATE TABLE rappels(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nom TEXT," +
            "dateExec TEXT," +
            "eventJSON TEXT" +
            ")";
}
