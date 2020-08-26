package com.kgtb32.icallib;

import android.util.Log;

import com.kgtb32.myedt.R;

public class NameTranslateHelper {

    public static int translateName(String val){
        if(val.equals("CATEGORIES")){
            return R.string.CATEGORIES;


        }
        else if(val.equals("LAST-MODIFIED")){
            return R.string.LAST_MODIFIED;
        }
        else if(val.equals("DESCRIPTION")){
            return R.string.DESCRIPTION;
        }
        else if(val.equals("LOCATION")){
            return R.string.LOCATION;
        }
        else{
            return -1;
        }
    }
}
