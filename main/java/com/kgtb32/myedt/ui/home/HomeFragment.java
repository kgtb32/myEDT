package com.kgtb32.myedt.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kgtb32.icallib.Event;
import com.kgtb32.myedt.MainActivity;
import com.kgtb32.myedt.R;
import com.kgtb32.myedt.model.DateSelectDialog;
import com.kgtb32.myedt.model.DialogDetailEvent;
import com.kgtb32.myedt.model.DialogNETError;
import com.kgtb32.myedt.model.Dialog_rappel;
import com.kgtb32.myedt.model.EdtHelper;
import com.kgtb32.myedt.model.EventListAdapter;
import com.kgtb32.myedt.model.PreLoadingDialog;
import com.kgtb32.myedt.model.SearchBar;
import com.kgtb32.myedt.model.database.RappelsDatabaseHelper;
import com.kgtb32.myedt.ui.kgFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment implements kgFragment {

    private EdtHelper helper;
    private MainActivity activity;

    private DateSelectDialog dateSelectDialog;
    private PreLoadingDialog preLoadingDialog;
    public SearchBar searchBarUtil;
    //private PostLoadingDialog postLoadingDialog;

    private Button btnDayMoins;
    private Button btnDayPlus;
    private Button btnDateChoose;
    private Button btnSearch;
    private Button btnRappels;

    private TextView date_text;

    private LinearLayout searchBar;

    private boolean showSearchBar = false;
    private Timer autoRefresh = new Timer();

    private View root;


    private ListView list;


    /**
     * Méthode addActions
     * Cette méthode permet d'ajouter les eventListener sur les widgets du fragment
     */
    private void addActions(){

        this.btnRappels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Dialog_rappel(HomeFragment.this).show();
            }
        });

        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new DialogDetailEvent(HomeFragment.this,(Event)HomeFragment.this.list.getAdapter().getItem(position)).show();
            }
        });

        this.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HomeFragment.this.showSearchBar){
                    HomeFragment.this.searchBar.setVisibility(View.GONE);
                    HomeFragment.this.showSearchBar = false;
                }
                else{
                    HomeFragment.this.searchBar.setVisibility(View.VISIBLE);
                    HomeFragment.this.showSearchBar = true;
                }
            }
        });

        this.btnDateChoose.setOnClickListener(new View.OnClickListener() { //ClickListener du bouton Calendrier
            @Override
            public void onClick(View v) {
                HomeFragment.this.dateSelectDialog.afficher();
            }
        });

        this.btnDayPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.this.activity.opDate(1);
                HomeFragment.this.getEdtHelper().setDate(HomeFragment.this.activity.getSelectedDate());
                HomeFragment.this.date_text.setText(HomeFragment.this.activity.getSelectedDate().toString());
                HomeFragment.this.getEdtHelper().execute();
            }
        });

        this.btnDayMoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.this.activity.opDate(-1);
                HomeFragment.this.getEdtHelper().setDate(HomeFragment.this.activity.getSelectedDate());
                HomeFragment.this.date_text.setText(HomeFragment.this.activity.getSelectedDate().toString());
                HomeFragment.this.getEdtHelper().execute();
            }
        });

    }

    /**
     * Méthode permettant de désérialiser uniquement les widgets du fragment.
     * @param root activité mère
     */
    public void Deserialiser(View root){
        this.activity = ((MainActivity) this.getActivity());
        this.btnDayMoins = root.findViewById(R.id.btnDayMoins);
        this.btnDayPlus = root.findViewById(R.id.btnDayPlus);
        this.btnDateChoose = root.findViewById(R.id.btnDayChoose);
        this.list = root.findViewById(R.id.List);
        this.btnSearch = root.findViewById(R.id.btnSearch);

        this.searchBar  = root.findViewById(R.id.search_bar);
        this.date_text = root.findViewById(R.id.date_text_home);
        this.searchBarUtil = new SearchBar(this,(Button)root.findViewById(R.id.btnSearchClose),(Button) root.findViewById(R.id.btnSearchOptions),this.searchBar,(EditText)root.findViewById(R.id.search_text_field));
        this.dateSelectDialog = new DateSelectDialog(this);
        this.preLoadingDialog = new PreLoadingDialog(this);
        this.root = root;
        this.btnRappels = root.findViewById(R.id.btnRappels);
        this.setTimer();
        this.addActions();
        }


    private void setTimer(){
        this.autoRefresh = new Timer();
        if(this.activity.getSettingsHelper().isAutoUpdateEnabled()) {
            this.autoRefresh.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    HomeFragment.this.getAct().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HomeFragment.this.setIsFirstStart(true);
                            HomeFragment.this.helper.execute();
                        }
                    });
                    //
                }
            }, this.activity.getSettingsHelper().getRefreshRate().toSec(), this.activity.getSettingsHelper().getRefreshRate().toSec());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.autoRefresh.cancel();
    }


    @Override
    public void onResume(){
        super.onResume();
        this.setTimer();
    }

    /**
     * Méthode onCreateView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Locale.setDefault(Locale.FRANCE); //on définit la langue du système (principalement pour traduire jour/mois du calendrier)
        final View root = inflater.inflate(R.layout.fragment_home, container, false); //cet fragment utilisera le layout fragment_home
        this.Deserialiser(root); //désérialisation des widgets du fragment
        final MainActivity activity =((MainActivity) this.getActivity()); //récupération de la vue de base (MainActivity)
        this.helper = new EdtHelper(activity.getSettingsHelper().getURL(),activity.getSettingsHelper().getFileType(),activity.getSettingsHelper().isCacheStorageEnabled(),this, this.getDate());//initialisation de l'objet helper
        this.date_text.setText(this.activity.getSelectedDate().toString());
        if(this.activity.getEvents().size() > 0){ //si les events stockés dans la MainActivity (classe mère) contient au moins 1 élément >> liste déjà affichée et doit être restorée
            this.updateList(); //appel de l'update de la liste
        }
        return root;
    }

    /**
     * Méthode notifyDownloadError
     * Cette méthode permet d'afficher un toaster "Erreur de téléchargement de l'emploi du temps"
     */
    @Override
    public void notifyDownloadError(){
        DialogNETError dialog = new DialogNETError(HomeFragment.this);
        dialog.show();
    }


    /**
     * Récupération de l'ArrayList des évents
     * @return ArrayList<Event>
     */
    @Override
    public ArrayList<Event> getEvents() {
        return this.activity.getEvents();
    }
    @Override
    public void setEvents(Event[] events) {
        this.activity.setEvents(events);
    }

    public void updateList(ArrayList<Event> events){
        this.searchBarUtil = null;
        this.list.setAdapter(new EventListAdapter(this.getContext(),R.layout.adapter_list_view,events));
        this.searchBarUtil = new SearchBar(this,(Button)root.findViewById(R.id.btnSearchClose),(Button) root.findViewById(R.id.btnSearchOptions),this.searchBar,(EditText)root.findViewById(R.id.search_text_field));
    }
    /**
     * Méthode updateList
     * on redéfinit l'adapter afin de mettre à jour la liste
     */
    @Override
    public void updateList(){
        this.searchBarUtil = null;
        this.list.setAdapter(new EventListAdapter(this.getContext(),R.layout.adapter_list_view,this.getEvents()));
        this.searchBarUtil = new SearchBar(this,(Button)root.findViewById(R.id.btnSearchClose),(Button) root.findViewById(R.id.btnSearchOptions),this.searchBar,(EditText)root.findViewById(R.id.search_text_field));
    }


    public EdtHelper getEdtHelper(){
        return this.helper;
    }

    public Date getDate(){
        return this.activity.getSelectedDate();
    }

    public void setDate(Date newDate){
        Log.e("DateUpdate",newDate.toString());
        this.activity.setSelectedDate(newDate);
        this.date_text.setText(this.activity.getSelectedDate().toString());
    }

    @Override
    public Activity getAct(){
        return this.activity;
    }

    @Override
    public PreLoadingDialog getPreLoadingDialog(){
        return this.preLoadingDialog;
    }

    @Override
    public boolean isFirstStart() {
        return this.activity.isFirstStart();
    }

    @Override
    public void setIsFirstStart(boolean newBool) {
        this.activity.setIsFirstStart(newBool);
    }

    @Override
    public Context getCont(){
        return  this.getContext();
    }
}