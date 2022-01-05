package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by samvision on 10/02/2017.
 */

public class Utility {

    public static void putbool(String key, Boolean value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static void putPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void Scrivichiavefile(Context context, String str, String str2, Boolean bool) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putBoolean(str2, bool.booleanValue());
        edit.commit();

    }
    public static void Scrivichiavefile(Context context, String str, String str2, String valorestringa) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putString(str2, valorestringa.toString());
        edit.commit();

    }
    public static void Scrivichiavefile(Context context, String str, String str2, Integer valorenumerico) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putInt(str2, valorenumerico);
        edit.commit();

    }

    public static Boolean leggichiavefile(Context context, String str, String str2, Boolean bool) {
        return Boolean.valueOf(context.getSharedPreferences(str, 0).getBoolean(str2, bool.booleanValue()));
    }
  //  @org.jetbrains.annotations.Contract("_, _, _, !null -> !null")
    public static String leggichiavefile(Context context, String str, String str2, String valorestringa) {
        return (context.getSharedPreferences(str, 0).getString(str2, valorestringa));
    }

    public static Integer leggichiavefile(Context context, String str, String str2, Integer valorenumerico) {
        return (context.getSharedPreferences(str, 0).getInt(str2, valorenumerico));
    }
    public static void Msg(Activity activity , String Titolo, String Messaggio) {
        AlertDialog.Builder B = new AlertDialog.Builder(activity);
        B.setTitle(Titolo);
        B.setMessage(Messaggio);
        B.show();
    }

   public static void scrivilog(final int level, final String data){

        new Thread(new Runnable() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            public void run() {
                // network stuff...

                Log.i(TAG,"provo a scrivere______________");
                try {
                    File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "OWL_logs");
                    if(!directory.exists())
                    {
                        directory.mkdir();
                        if(!directory.exists())
                        {
                            Log.e(TAG,"WARNING! Directory does not exists !!!! Creation problems");
                        }
                    }
                    //String currentDateString = new SimpleDateFormat("yyyyMMdd").format(new Date() );

                    String currentTimeString = new SimpleDateFormat("HH:mm").format(new Date());
                    String currentDateString =new SimpleDateFormat("YYMMdd").format(new Date());
                    String filename = currentDateString + "_worker.txt";
                    File newFile = new File(directory, filename);
                    if(!newFile.exists()){
                        try {
                            newFile.createNewFile();
                        } catch (IOException e) {
                            Log.e(TAG,"problema creazione file "+e.getMessage());
                        }
                    }
                    else
                    {
                    }
                    try  {
                        FileOutputStream fOut = new FileOutputStream( newFile, true ); // NECESSARY CONSTRUCTOR TO APPEND
                        OutputStreamWriter outputWriter=new OutputStreamWriter(fOut);
                        outputWriter.append(currentTimeString + ": (" + String.valueOf(level) + ") " + data);
                        outputWriter.append("\n\r");
                        outputWriter.close();
                    }catch (Exception e){
                        Log.e(TAG,"problema scrittura file" +e.getMessage());
                    }
                }
                catch (Exception e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }

        }).start();
    }
    public static void simulateSendKey(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    for ( int i = 0; i < 2; ++i ) {
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_1);
                        Thread.sleep(200);
                        /*inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        Thread.sleep(1000);*/
                        Log.i(TAG,"invio un tasto");
                    }
                }
                catch(InterruptedException e){
                }
            }
        }).start();
    }
  /*  public   void prendimetodoinput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> mInputMethodProperties = imm.getEnabledInputMethodList();

        final int n = mInputMethodProperties.size();

        for (int i = 0; i < n; i++) {
            InputMethodInfo imi = mInputMethodProperties.get(i);
            Log.d("TAG", "Input Method ID: "+ getApplicationContext().getPackageName()); }
    }*/

 public void  sendKey(){
    /* BaseInputConnection inputConnection = new BaseInputConnection(edittext, true);
     inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BUTTON_B));
     inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_POUND));}*/
}

}
