package com.kgtb32.myedt.model.enums;

public enum EnumRefreshRate {
    T5Mn,
    T10Mn,
    T15Mn,
    T30Mn,
    T1H,
    T2H,
    T12H,
    T24H;

    public static  EnumRefreshRate toEnum(int data){
        EnumRefreshRate[] tab = {EnumRefreshRate.T5Mn, EnumRefreshRate.T10Mn,EnumRefreshRate.T15Mn,EnumRefreshRate.T30Mn,EnumRefreshRate.T30Mn,EnumRefreshRate.T1H,EnumRefreshRate.T12H,EnumRefreshRate.T24H};
        return tab[data];
    }

    public int toInt(){
        switch(this){
            case T5Mn:
                return 0;
            case T10Mn:
                return 1;
            case T15Mn:
                return 2;
            case T30Mn:
                return 3;
            case T1H:
                return 4;
            case T2H:
                return 5;
            case T12H:
                return 6;
            case T24H:
                return 7;
            default:
                return -1;
        }
    }
    public int toSec(){
        switch(this){
            case T5Mn:
                return 1000*60*5;
            case T10Mn:
                return 1000*60*10;
            case T15Mn:
                return 1000*60*15;
            case T30Mn:
                return 1000*60*30;
            case T1H:
                return 1000*60*60;
            case T2H:
                return 1000*60*60*2;
            case T12H:
                return 1000*60*60*12;
            case T24H:
                return 1000*60*60*24;
            default:
                return -1;
        }
    }
}
