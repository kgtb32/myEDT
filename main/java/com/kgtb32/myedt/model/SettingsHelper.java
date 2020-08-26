package com.kgtb32.myedt.model;

import androidx.appcompat.app.AppCompatActivity;

import com.kgtb32.myedt.R;
import com.kgtb32.myedt.model.enums.EnumRefreshRate;
import com.kgtb32.myedt.model.enums.FileType;

public class SettingsHelper {
    private int ThemeID;
    private String themeName;
    private FileType.EnumFileType fileType;
    private String URL;
    private boolean cacheStorage;
    private boolean autoUpdate;
    private AppCompatActivity activity;
    private EnumRefreshRate refreshRate;

    public SettingsHelper(AppCompatActivity activity, int ThemeID, String themeName, FileType.EnumFileType fileType, String URL, boolean cacheStorage, boolean autoUpdate, EnumRefreshRate refreshRate){
        this.activity = activity;
        this.ThemeID = ThemeID;
        this.themeName = themeName;
        this.fileType = fileType;
        this.URL = URL;
        this.cacheStorage = cacheStorage;
        this.autoUpdate = autoUpdate;
        this.refreshRate = refreshRate;
    }

    public SettingsHelper(AppCompatActivity activity){
        this.activity = activity;
    }

    public void defaultVals(){
        this.ThemeID = R.style.Bleu_Ciel;
        this.themeName = this.activity.getString(R.string.theme_blue_name);
        this.fileType = FileType.EnumFileType.typeWebURL;
        this.cacheStorage = true;
        this.autoUpdate = false;
        this.refreshRate = EnumRefreshRate.T5Mn;
        this.URL = "";
    }

    /**
     * Getters
     */


    /**
     * Méthode getThemeID >> Récupération de l'ID du thème activé dans les paramètres
     * @return ThemeID : INT
     */
    public int getThemeId(){
        return this.ThemeID;
    }

    /**
     * Méthode getThemeName >> Récupération du nom du thème activé dans les paramètres
     * @return ThemeName : String
     */

    public String getThemeName(){
        return this.themeName;
    }

    /**
     * Méthode getFileType >> Récupération du type de fichier utilisé défini dans les paramètres
     * @return FileType : EnumFileType (typeICSFile / typeWebURL)
     */

    public FileType.EnumFileType getFileType(){
        return this.fileType;
    }

    /**
     * Méthode isCacheStorageEnabled >> Activation ou non du stockage en cache des informations téléchargées.
     * @return cacheStorage : boolean
     */

    public boolean isCacheStorageEnabled(){
        return this.cacheStorage;
    }

    /**
     * Méthode isAutoUpdateEnabled >> Activation ou non de la mise à jour automatique des informations
     * @return autoUpdate : boolean
     */

    public boolean isAutoUpdateEnabled(){
        return this.autoUpdate;
    }

    public String getURL(){
        return this.URL;
    }

    /**
     * Setters
     */


    /**
     * Méthode setThemeID : Permet de définir l'ID du theme à définir
      * @param newID
     */

    public void setThemeID(int newID){
        this.ThemeID = newID;
    }

    public void setThemeName(String newName){
        this.themeName = newName;
    }

    public void setFileType(FileType.EnumFileType newType){
        this.fileType = newType;
    }

    public void setURL(String newURL){
        this.URL = newURL;
    }

    public void setAutoUpdateEnabled(boolean newState){
        this.autoUpdate = newState;
    }

    public void setCacheStorageEnabled(boolean newState){
        this.cacheStorage = newState;
    }

    public EnumRefreshRate getRefreshRate(){
        return this.refreshRate;
    }

    public void setRefreshRate(EnumRefreshRate refreshRate){
        this.refreshRate = refreshRate;
    }







}
