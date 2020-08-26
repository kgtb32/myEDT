package com.kgtb32.myedt.model.database;

public class FilterDatabaseConstantes {
    public static String DATABASE_NAME = "Filters.db";
    public static int DATABASE_VERSION = 1;
    public static String SCRIPT_ONUPGRADE = "DROP TABLE IF EXISTS filtres;";
    public static String SCRIPT_CREATE = "CREATE TABLE filtres (" +
            "id INT PRIMARY KEY," +
            "champ TEXT NOT NULL," +
            "operation INT NOT NULL," +
            "valeur TEXT NOT NULL," +
            "action INT NOT NULL," +
            "data TEXT)";
}
