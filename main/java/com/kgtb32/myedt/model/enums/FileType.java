package com.kgtb32.myedt.model.enums;

public class FileType {
    public enum EnumFileType{
        typeFileICS,
        typeWebURL; //WebURL : Default
        public String toString(){
            switch(this){
                case typeFileICS:
                    return "typeFileICS";
                default:
                    return "typeWebURL";
            }
        }
        public int toInt(){
            switch (this){
                case typeFileICS:
                    return 0;
                default:
                    return 1;
            }
        }
    }


    public static  EnumFileType toEnum(int pos){
        if(pos == 0){
            return EnumFileType.typeFileICS;
        }
        else{
            return EnumFileType.typeWebURL;
        }
    }

    public static EnumFileType toEnum(String str){
        if(str.equals("typeFileICS")){
            return EnumFileType.typeFileICS;
        }
        else{
            return EnumFileType.typeWebURL;
        }
    }


}
