package com.kgtb32.myedt.ui.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;

import com.codekidlabs.storagechooser.StorageChooser;
import com.kgtb32.myedt.MainActivity;
import com.kgtb32.myedt.R;
import com.kgtb32.myedt.model.Dialog_Color_Select;
import com.kgtb32.myedt.model.Dialog_URL_Edit;
import com.kgtb32.myedt.model.SettingsHelper;
import com.kgtb32.myedt.model.enums.EnumRefreshRate;
import com.kgtb32.myedt.model.enums.FileType;
import com.kgtb32.myedt.ui.home.HomeFragment;

import org.honorato.multistatetogglebutton.ToggleButton;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Set;


public class SettingsFragment extends Fragment {

    private Button btnThemeUpdate;
    private MainActivity activity;
    private EditText urlField;
    private Button btnValidate;
    private Button btnURLupdate;

    private TextView txtThemeName;

    private Switch switch_cacheStorage;
    private Switch switch_autoRefresh;

    private org.honorato.multistatetogglebutton.MultiStateToggleButton refreshRateWidget;
    private org.honorato.multistatetogglebutton.MultiStateToggleButton fileTypeWidget;

    private Button btnFileUpdate;


    private void updateFileTypeBtns(){
        FileType.EnumFileType e = this.activity.getSettingsHelper().getFileType();
        Log.e("type",e.toString());
        if(e.equals(FileType.EnumFileType.typeFileICS)){
            this.btnFileUpdate.setVisibility(View.VISIBLE);
            this.btnURLupdate.setVisibility(View.GONE);
        }
        else{
            this.btnURLupdate.setVisibility(View.VISIBLE);
            this.btnFileUpdate.setVisibility(View.GONE);
        }
    }

    private void addActions(){
        //TODO edit this
        this.fileTypeWidget.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                SettingsHelper h = SettingsFragment.this.activity.getSettingsHelper();
                h.setFileType(FileType.toEnum(value));
                SettingsFragment.this.activity.getSettingsHelper().setFileType(h.getFileType());
                SettingsFragment.this.activity.getStorageHelper().saveSettingsData(h);
                SettingsFragment.this.activity.reloadSettingsHelper();
                SettingsFragment.this.updateFileTypeBtns();
            }
        });

        this.btnFileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("text/calendar");
                SettingsFragment.this.startActivityForResult(i.createChooser(i,"Sélectionnez un fichier ICS"),5);*/
                // 1. Initialize dialog
                final StorageChooser chooser = new StorageChooser.Builder()
                        // Specify context of the dialog
                        .withActivity(SettingsFragment.this.activity)
                        .withFragmentManager(SettingsFragment.this.activity.getFragmentManager())
                        .withMemoryBar(true)
                        .allowCustomPath(true)
                        .filter(StorageChooser.FileType.)
                        // Define the mode as the FILE CHOOSER
                        .setType(StorageChooser.FILE_PICKER)
                        .build();

// 2. Handle what should happend when the user selects the directory !
                chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
                    @Override
                    public void onSelect(String path) {
                        // e.g /storage/emulated/0/Documents/file.txt
                        SettingsFragment.this.activity.getSettingsHelper().setURL(path);
                        SettingsFragment.this.activity.getStorageHelper().saveSettingsData(SettingsFragment.this.activity.getSettingsHelper());
                        Toast.makeText(SettingsFragment.this.getContext(),"Le fichier à été séléctionné.", Toast.LENGTH_LONG);
                    }
                });
                chooser.show();
            }
        });



        this.btnThemeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Dialog_Color_Select(SettingsFragment.this).show();
            }
        });

        this.btnURLupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_URL_Edit diag = new Dialog_URL_Edit(SettingsFragment.this);
                diag.show();
            }
        });

        this.refreshRateWidget.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                SettingsFragment.this.activity.getSettingsHelper().setRefreshRate(EnumRefreshRate.toEnum(value));
                SettingsFragment.this.activity.getStorageHelper().saveSettingsData(SettingsFragment.this.activity.getSettingsHelper());
            }
        });

        this.switch_autoRefresh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsFragment.this.activity.getSettingsHelper().setAutoUpdateEnabled(isChecked);
                SettingsFragment.this.activity.getStorageHelper().saveSettingsData(SettingsFragment.this.activity.getSettingsHelper());
                //Mise à jour de l'UI
                if(isChecked){
                    SettingsFragment.this.refreshRateWidget.setVisibility(View.VISIBLE);
                }
                else{
                    SettingsFragment.this.refreshRateWidget.setVisibility(View.GONE);
                }

            }
        });

    }

    private void déserialiser(View root){
        this.activity =((MainActivity) SettingsFragment.this.getActivity());
        this.btnThemeUpdate = root.findViewById(R.id.btnUpdate);
        this.btnURLupdate = root.findViewById(R.id.URLupdateBtn);
        this.btnFileUpdate = root.findViewById(R.id.setttings_file_type_btn);
        this.switch_cacheStorage = root.findViewById(R.id.switch_cache_storage);
        this.txtThemeName = root.findViewById(R.id.appSettings_txtThemeName);
        this.txtThemeName.setText(this.activity.getSettingsHelper().getThemeName());
        this.switch_autoRefresh = root.findViewById(R.id.switch_autoUpdate);
        this.switch_autoRefresh.setChecked(this.activity.getSettingsHelper().isAutoUpdateEnabled());
        this.fileTypeWidget = root.findViewById(R.id.appSettings_fileType);
        this.fileTypeWidget.setElements(R.array.fileType,this.activity.getSettingsHelper().getFileType().toInt());
        this.refreshRateWidget = root.findViewById(R.id.appSettings_autoRefresh);
        this.refreshRateWidget.setElements(R.array.refreshArray,this.activity.getSettingsHelper().getRefreshRate().toInt());

        FileType.EnumFileType fileType = this.activity.getSettingsHelper().getFileType();
        if(fileType == FileType.EnumFileType.typeFileICS){
            Log.e("i","in2");

        }
        else{
            Log.e("i","in");
        }
        this.addActions();

    }

    private void désérialiserURLDialog(View root){
        this.urlField = root.findViewById(R.id.dialog_URL_field);
        this.btnValidate  = root.findViewById(R.id.dialog_URLbtnValidate);
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_settings, container, false);
        this.déserialiser(root);
        this.updateFileTypeBtns();
        return root;
    }



   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 5: //retour du file Chooser
                if(resultCode == Activity.RESULT_OK){
                    String path = data.getData().getPath();
                    SettingsFragment.this.activity.getSettingsHelper().setURL(path);
                    SettingsFragment.this.activity.getStorageHelper().saveSettingsData(SettingsFragment.this.activity.getSettingsHelper());
                    Log.e("DIR",path);
                }

        }
    }*/

    public MainActivity getAct(){
        return this.activity;
    }

    public void setTheme(String name, int ThemeID){
        SettingsFragment.this.activity.getSettingsHelper().setThemeName(name);
        SettingsFragment.this.activity.getSettingsHelper().setThemeID(ThemeID);
        SettingsFragment.this.activity.getStorageHelper().saveSettingsData(SettingsFragment.this.activity.getSettingsHelper());
        SettingsFragment.this.activity.refresh();
    }
}