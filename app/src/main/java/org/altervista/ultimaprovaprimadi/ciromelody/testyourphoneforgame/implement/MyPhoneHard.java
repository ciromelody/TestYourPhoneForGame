package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement;

import android.os.Build;

/**
 * Created by gustav on 20/08/2017.
 */

 public  class MyPhoneHard {

    public String getSeriale() {
      //  if (Build.VERSION.SDK_INT >= 28) { return seriale= "non disponibile per android 9";}
        return seriale= "SERIAL:"+ Build.SERIAL;
    }

    private String seriale;

    public String getDetail() {

            detail= "BOARD:"+ Build.BOARD+"\n"+ "ID:"+ Build.ID+"\n"+"DEVICE:"+ Build.DEVICE+"\n"+
                    "BRAND:"+ Build.BRAND+"\n"+ "DISPLAY:"+ Build.DISPLAY+"\n"+"HARDWARE:"+ Build.HARDWARE+"\n"+
                    "MANUFACTURER:"+ Build.MANUFACTURER+"\n"+"MODEL:"+ Build.MODEL+"\n"+"PRODOTTO:"+
                    Build.PRODUCT+"\n"+"SERIALE TRONCATO:"+
                    truncate(Build.SERIAL, 4 ,14)+"\n"+
                    "VERSIONE ANDROID:"+ Build.VERSION.SDK_INT+"\n"+
                    "VERSIONE ANDROID :"+ Build.VERSION.CODENAME+"\n"+
                      "UTENTE :"+ Build.USER+"\n"


                   ;


        return detail;
    }

    private String detail;


    private String truncate(String str, int inizio , int len) {
       // if (Build.VERSION.SDK_INT >= 28) { return seriale= "non disponibile per android 9";}
        if (str.length() > len) {
            return "..."+str.substring(inizio, len) + "...";
        } else {
            return str;
        }}
}