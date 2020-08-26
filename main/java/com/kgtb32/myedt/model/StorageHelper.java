package com.kgtb32.myedt.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.kgtb32.myedt.model.enums.EnumRefreshRate;
import com.kgtb32.myedt.model.enums.FileType;

public class StorageHelper {
    private AppCompatActivity activity;

    private static String appPrefix = "MyEDT";

    public StorageHelper(AppCompatActivity activity){
        this.activity = activity;
    }

    public void saveSettingsData(SettingsHelper dataToSave){
        SharedPreferences file = this.activity.getSharedPreferences(StorageHelper.appPrefix,Context.MODE_PRIVATE);
        SharedPreferences.Editor data = file.edit();
        data.putInt("themeID",dataToSave.getThemeId());
        data.putString("themeName",dataToSave.getThemeName());
        data.putString("URL",dataToSave.getURL());
        data.putString("fileType", dataToSave.getFileType().toString());
        //TODO ajout du type de fichier
        data.putBoolean("cacheStorage",dataToSave.isCacheStorageEnabled());
        data.putBoolean("autoUpdate",dataToSave.isAutoUpdateEnabled());
        data.putInt("refreshRate",dataToSave.getRefreshRate().toInt());
        data.commit();
    }

    public SettingsHelper loadSettingsData(){
        SettingsHelper defaultSettings = new SettingsHelper(this.activity);
        defaultSettings.defaultVals();
        SharedPreferences data = this.activity.getSharedPreferences(StorageHelper.appPrefix, Context.MODE_PRIVATE); //initialisation de l'objet pour la récupération des valeurs
        return new SettingsHelper(
                this.activity,
                data.getInt("themeID", defaultSettings.getThemeId()),
                data.getString("themeName",defaultSettings.getThemeName()),
                FileType.toEnum(data.getString("fileType","TypeWebURL")),
                data.getString("URL",defaultSettings.getURL()),
                data.getBoolean("cacheStorage",defaultSettings.isCacheStorageEnabled()),
                data.getBoolean("autoUpdate",defaultSettings.isAutoUpdateEnabled()),
                EnumRefreshRate.toEnum(data.getInt("refreshRate",defaultSettings.getRefreshRate().toInt()))
        );
    }
}
