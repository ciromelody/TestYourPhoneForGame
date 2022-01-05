package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class WriteFileTest extends AppCompatActivity {
TextView textView;
    final private int REQUEST_READ_EXTERNAL_STORAGE = 9;
    final private int REQUEST_WRITE_EXTERNAL_STORAGE = 10;
    private static final int MY_PERMISSION_REQUEST_LOCATION = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);
        if(SDK_INT<23){
            Utility.scrivilog(SDK_INT, "Scrivo senza chiedere  permessi");
        }
        if((SDK_INT>=23)&&(SDK_INT<27)) {
            Log.d("MOM","Version sdk:"+ SDK_INT);
            chiediipermessiscritturalettura();
        }
        if(SDK_INT>=27){
            //ToDo
            if(Utility.leggichiavefile(this,"SDK11 E SUPERIORE","PERMESSI_SCRITTURA",false)){
                Utility.scrivilog(SDK_INT, "Scrivo senza chiedere più permessi");
                textView.setText("permessi di scrittura concessi!Puoi scrivere file sul dispositivo");
            }else {
                requestPermission();
            }
        }
    }
    private void chiediipermessiscritturalettura() {
        if (ContextCompat.checkSelfPermission(this,  android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,  WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED    ) {

            ActivityCompat.requestPermissions(this,
                    new String[]{ android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE);

            ActivityCompat.requestPermissions(this,
                    new String[]{ WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
            textView.setText("permessi di scrittura concessi!Puoi scrivere file sul dispositivo");
            Toast.makeText(this, "Permission Log File ottenuti ", Toast.LENGTH_SHORT).show();
            Utility.scrivilog(SDK_INT, "Scrivo senza chiedere più permessi");
            //ReadExternalStorage();
            // non fare niente l'activity serve solo per ottenere i permessi di lettura
            // finish();//esco
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE :
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];

                    if (permission.equals( android.Manifest.permission.READ_EXTERNAL_STORAGE) || permission.equals( READ_EXTERNAL_STORAGE)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            // ReadExternalStorage();
                            textView.setText("permessi di scrittura concessi!Puoi scrivere file sul dispositivo");
                            Utility.scrivilog(SDK_INT, "Versione sdk ha ottenuto i permessi di scrittura");
                            Toast.makeText(this, "Permission read File ottenuti vedi in download", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Permission read File Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
            case REQUEST_WRITE_EXTERNAL_STORAGE :
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];

                    if (permission.equals( WRITE_EXTERNAL_STORAGE) || permission.equals( WRITE_EXTERNAL_STORAGE)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            // ReadExternalStorage();
                            Utility.scrivilog(SDK_INT, "Versione sdk ha ottenuto i permessi di scrittura");
                            Toast.makeText(this, "Permission write File ottenuti vedi in download", Toast.LENGTH_SHORT).show();
                            textView.setText("permessi di scrittura concessi!Puoi scrivere file sul dispositivo");
                        } else {
                            Toast.makeText(this, "Permission write File Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case MY_PERMISSION_REQUEST_LOCATION:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals( Manifest.permission.ACCESS_FINE_LOCATION) || permission.equals( Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            // ReadExternalStorage();
                            Toast.makeText(this, "Permission Log File ottenuti vedi in download", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(this, "Permission Log File Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void requestPermission() {
        if (SDK_INT > Build.VERSION_CODES.Q) {
            try {

                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2237);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2237);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(WriteFileTest.this, new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE );
            ActivityCompat.requestPermissions(WriteFileTest.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE );
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2237) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    textView.setText("permessi di scrittura concessi!Puoi scrivere file sul dispositivo");
                    Utility.scrivilog(SDK_INT, "Versione sdk ha ottenuto i permessi di scrittura");
                    Utility.Scrivichiavefile(this,"SDK11 E SUPERIORE","PERMESSI_SCRITTURA",true);
                    Log.d("MOM","perform action");
                    Toast.makeText(this, "Buono puoi scrivere file!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}