package com.kgtb32.myedt.model.enums;


public enum EnumOperation{
        CONTAINS,
        EQUALS,
        NOT_CONTAINS,
        NOT_EQUALS;

        public boolean doOp(String value, String test){
            switch (this){
                case CONTAINS:
                    if(value.contains(test)){
                        return true;
                    }
                    else{
                        return  false;
                    }
                case EQUALS:
                    if(value.equals(test)){
                        return true;
                    }
                    else{
                        return false;
                    }
                case NOT_CONTAINS:
                    if(!value.contains(test)){
                        return true;
                    }
                    else{
                        return false;
                    }
                case NOT_EQUALS:
                    if(!value.equals(test)){
                        return true;
                    }
                    else{
                        return false;
                    }
                default:
                    return false;
            }
        }
}

