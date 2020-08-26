package com.kgtb32.myedt;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kgtb32.icallib.Event;
import com.kgtb32.myedt.model.SettingsHelper;
import com.kgtb32.myedt.model.StorageHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private boolean isFirstStart = true;
    private int themeID;

    private SettingsHelper settings;
    private StorageHelper storageHelper;

    private Date selectedDate;
    private ArrayList<Event> events = new ArrayList<Event>();



    /**
     *
     * Méthode refresh
     * Cette méthode est appelée lors de la validation du changement de thème dans les paramètres de l'application
     * Elle permet de recréer l'intégralité de l'UI afin de mettre à jour le theme qui ne peut se mettre à jour QUE
     * avant l'appel de la fonction super.onCreate() dans la fonction onCreate de cette classe
     *
     */

    public void refresh(){
        this.recreate();
    }


    /**
     *
     * Méthode onCreate(
     * @param savedInstanceState Bundle)
     * *Cette méthode est executée lors du lancement de l'activité par Android
     * *Elle a pour but d'initialiser le theme, désérialiser les éléments qui composent
     * *cette activité dont l'initialisation du BottomNavigationView
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.storageHelper = new StorageHelper(this);
        this.settings = this.storageHelper.loadSettingsData();
        this.selectedDate = Calendar.getInstance().getTime();

        Log.e("Theme : ",this.settings.getThemeId()+"");
        this.setTheme(this.settings.getThemeId()); //Définition du theme >> Le theme doit ABSOLUMENT être défini AVANT l'appel de la fonction super.onCreate()
        super.onCreate(savedInstanceState); //appel de la fonction onCreate de la classe étendue

        //Ajout et action des fragements pour la menubar
        setContentView(R.layout.activity_main); //Définition du layout
        BottomNavigationView navView = findViewById(R.id.nav_view); //Récupération de la BottomNavigationBar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_settings, R.id.navigation_about).build(); //Initialisation de la configuration : Éléments à placer dans le BottomNav
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment); //Controlleur pour la BottomBar
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); //Assemblage du controlleur et de la configuration
        NavigationUI.setupWithNavController(navView, navController); //Assemblage du controlleur et de la configuration

        //----------------------





    }

    public StorageHelper getStorageHelper(){
        return this.storageHelper;
    }

    public SettingsHelper getSettingsHelper(){
        return this.settings;
    }

    public void reloadSettingsHelper(){
        this.settings = this.storageHelper.loadSettingsData();
    }

    public void opDate(int dir){
        Calendar c = Calendar.getInstance();
        c.setTime(this.selectedDate);
        c.add(Calendar.DATE,dir);
        this.selectedDate = c.getTime();
    }

    public void setSelectedDate(Date newDate){
        this.selectedDate = newDate;
    }

    public Date getSelectedDate(){
        return this.selectedDate;
    }

    public ArrayList<Event> getEvents(){
        return this.events;
    }

    public void setEvents(Event[] events){
        this.events.clear();
        for(int i=0;i<events.length;i++){
            this.events.add(events[i]);
        }
    }


    public boolean isFirstStart(){
        return this.isFirstStart;
    }

    public void setIsFirstStart(boolean newBool){
        this.isFirstStart = newBool;
    }
}
