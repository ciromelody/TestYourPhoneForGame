package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class CompassHolderTest extends AppCompatActivity implements SensorEventListener {
 TextView textView;
float yaw;
float pitch;
float roll;
Sensor mAccelerometro;
Sensor mMagnetometro;
    SensorManager manager;
//Sensor mSensorManager;
float[] mUltimaAccelerazione =new float[3];
float[] mUltimaMagnetometro =new float[3];
float[] mR=new float[9];
float[] mOrientamento =new float[3];
boolean mAccelerazioneSettata=false;
boolean mMagnetometroSettato=false;
StringBuilder builder = new StringBuilder();
Long tempoPrecedente,tempoAttuale,tempoDifferenza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);
        tempoPrecedente=System.currentTimeMillis();
        builder.append("controllo sensore accelerometro e magnetico"); textView.setText(builder.toString());
         manager =(SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0) {
            builder.append("non è presente il sensore accelerometro"); textView.setText(builder.toString());
        } else {
           mAccelerometro = manager.getSensorList(
                    Sensor.TYPE_ACCELEROMETER).get(0);
            if (!manager.registerListener(this, mAccelerometro,
                    SensorManager.SENSOR_DELAY_GAME)) {
                builder.append("l'accelerometro non può essere registrato"); textView.setText(builder.toString());
            }
        }
        if (manager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).size() == 0) {
            builder.append("non è presente il sensore magnetico"); textView.setText(builder.toString());
        } else {
            mMagnetometro = manager.getSensorList(
                    Sensor.TYPE_MAGNETIC_FIELD).get(0);
            if (!manager.registerListener(this, mMagnetometro,
                    SensorManager.SENSOR_DELAY_GAME)) {
                builder.append("Non può registrare il sensore magnetico"); textView.setText(builder.toString());
            }
        }
        mAccelerometro=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mMagnetometro=manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor==mAccelerometro){
            System.arraycopy(event.values,0,mUltimaAccelerazione,0,event.values.length);
            mAccelerazioneSettata=true;
            //builder.append(" è presente il sensore accelerometro"); textView.setText(builder.toString());
        }else {//builder.append("Non è presente il sensore accelerometro"); textView.setText(builder.toString());
        }

        if(event.sensor==mMagnetometro){
            System.arraycopy(event.values,0,mUltimaMagnetometro,0,event.values.length);
            mMagnetometroSettato=true;
          //  builder.append(" è presente il sensore magnetotermico"); textView.setText(builder.toString());
        }else {//builder.append("Non è presente il sensore magnetotermico"); textView.setText(builder.toString());
                                           }

         if(mAccelerazioneSettata&&mMagnetometroSettato){
            SensorManager.getRotationMatrix(mR,null,mUltimaAccelerazione,mUltimaMagnetometro);
            SensorManager.getOrientation(mR,mOrientamento);
            yaw=mOrientamento[0];
            pitch=mOrientamento[1];
            roll=mOrientamento[2];
            //scrivi su riga di testo
             tempoAttuale=System.currentTimeMillis();
             tempoDifferenza=tempoAttuale-tempoPrecedente;
             if (tempoDifferenza>1000){
                      builder.setLength(0);
                      builder.append("yaw: ");
                      builder.append( yaw);
                      builder.append(", pitch: ");
                      builder.append(pitch);
                      builder.append(", roll: ");
                      builder.append(roll);
                      textView.setText(builder.toString());
                      tempoPrecedente=tempoAttuale;
             }
        }

    }
    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}