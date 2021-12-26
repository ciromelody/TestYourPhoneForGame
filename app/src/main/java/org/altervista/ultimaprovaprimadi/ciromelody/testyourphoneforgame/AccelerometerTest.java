package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import static android.os.SystemClock.uptimeMillis;


public class AccelerometerTest extends AppCompatActivity implements SensorEventListener {
    TextView textView;
    StringBuilder builder = new StringBuilder();
    int int1,int2,int4;
   Long inizioTempo=0L;
   Long fineTempo=0L;
    Long deltaTempo;
     float numerofloat1 ;
     float numerofloat2 ;
     float numerofloat3 ;
     float numeofloat=10.0f;
     boolean boolean1=false;
     boolean boolean2 = false;
     Long intlong1=0L,intlong2=0L;

    SoundPool soundPool;
    int caricamento_suono = -1;
    int sparo_suono = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);
        preparaCaricatore();
        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0) {
            textView.setText("No accelerometer installed");
        } else {
            Sensor accelerometer = manager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            if (!manager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_GAME)) {
                textView.setText("Couldn't register sensor listener");
            }
        }
        int1=0;int2=0;int4=0;
    }

    private void preparaCaricatore() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        try {
            AssetManager assetManager = this.getAssets();
            AssetFileDescriptor descriptor = assetManager.openFd("caricamentofucile.wav");
            caricamento_suono = soundPool.load(descriptor, 1);
        } catch (IOException e) {
            textView.setText("Couldn't load sound effect from asset, "
                    + e.getMessage());
        }
        try {
            AssetManager assetManager = this.getAssets();
            AssetFileDescriptor descriptor = assetManager.openFd("sparo.wav");
            sparo_suono = soundPool.load(descriptor, 1);
        } catch (IOException e) {
            textView.setText("Couldn't load sound effect from asset, "
                    + e.getMessage());
        }
    }


    public void onSensorChanged(SensorEvent event) {

        fineTempo=System.currentTimeMillis();
        deltaTempo=fineTempo-inizioTempo;
        calcoliAggiuntivi(event);

        if (deltaTempo<600){
            float  x=event.values[0];
            float y =event.values[1];
            float z= event.values[2];
           builder.setLength(0);
           builder.append("x: ");
           builder.append(x);
           builder.append(", y: ");
           builder.append(y);
           builder.append(", z: ");
           builder.append(z);


        }else {
            textView.setText(builder.toString());
            inizioTempo=fineTempo;
        }
    }

    private void calcoliAggiuntivi(SensorEvent sensorEvent) {
        float f = sensorEvent.values[0];
        float f2 = sensorEvent.values[1];
        float f3 = sensorEvent.values[2];
       // boolean1=true;
        if(boolean1){
            float abs = Math.abs(this.numerofloat1 - f);
            float abs2 = Math.abs(this.numerofloat2 - f2);
            float abs3 = Math.abs(this.numerofloat3 - f3);
            //sensibilita accelerometro
             //numeofloat=14.0f;
            if (abs < numeofloat) {
                abs = 0.0f;

            }
            if (abs2 < numeofloat) {
                abs2 = 0.0f;
            }

            if (abs3 < numeofloat) {
                abs3 = 0.0f;
            }
            if(abs > 0.0f || abs2 > 0.0f || abs3 > 0.0f){
                Toast.makeText(getApplicationContext(), "shake", Toast.LENGTH_SHORT).show();
            }
            if (this.boolean2) {

                if (this.boolean2) {

                    if ((uptimeMillis() - intlong1 < 500) && (abs > 0.0f || abs2 > 0.0f || abs3 > 0.0f)){
                        calcolaAllelerazioneOrizVert(abs, abs2, abs3);
                        Log.d("TEST","sono passato da qui");


                } else if (abs == 0.0f && abs2 == 0.0f && abs3 == 0.0f) {
                    if (this.int1 >= this.int2 && this.int1 >= this.int4) {
                        Toast.makeText(getApplicationContext(), "Sensor Change horizontat", Toast.LENGTH_SHORT).show();
                        if (sparo_suono != -1) {
                            soundPool.play(sparo_suono, 1, 1, 0, 0, 1);
                        }

                    } else if (this.int2 >= this.int1 && this.int2 >= this.int4) {
                        if (caricamento_suono != -1) {
                            soundPool.play(caricamento_suono, 1, 1, 0, 0, 1);
                        }
                        Toast.makeText(getApplicationContext(), "Sensor Change vertical ", Toast.LENGTH_SHORT).show();
                    } else if (this.int4 >= this.int1 && this.int4 >= this.int2) {

                        Toast.makeText(getApplicationContext(), "Sensor Change forward ", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        //controllaseloScreenOnTelefonoinChiamata();
                        // EseguiFlash();
                        // Toast.makeText(getApplicationContext(), "change", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error. Responding to shake failed", Toast.LENGTH_LONG).show();
                    }
                    this.int1 = 0;
                    this.int2 = 0;
                    this.int4 = 0;
                    this.boolean2 = false;
                    this.intlong2 = uptimeMillis();
                }
            } //inizialmente era 500
            }else if (uptimeMillis() - this.intlong2 > 500 && (abs > 0.0f || abs2 > 0.0f || abs3 > 0.0f)) {

                     //controllaseloScreenOnTelefonoinChiamata();
                    Toast.makeText(getApplicationContext(), "Sensor Change intlong2 ", Toast.LENGTH_SHORT).show();
                    // EseguiFlash();
                    this.boolean2 = true;
                    calcolaAllelerazioneOrizVert(abs, abs2, abs3);
                    this.intlong2 = uptimeMillis();
                   //  intlong1=uptimeMillis(); //da cancellare se non va bene
            }
            this.numerofloat1 = f;
            this.numerofloat2 = f2;
            this.numerofloat3 = f3;
            return;
        }

        this.numerofloat1 = f;
        this.numerofloat2 = f2;
        this.numerofloat3 = f3;
        this.boolean1 = true;
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }
    private void calcolaAllelerazioneOrizVert(float f, float f2, float f3) {
        if (f > f2 && f > f3) {
            this.int1++;
        } else if (f2 > f && f2 > f3) {
            this.int2++;
        } else if (f3 > f && f3 > f2) {
            this.int4++;
        }
    }
}

