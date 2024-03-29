package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class ExternalStorageTest extends AppCompatActivity {
    final private int REQUEST_READ_EXTERNAL_STORAGE = 123;
    final private int REQUEST_WRITE_EXTERNAL_STORAGE = 123;
    TextView textView ;
    TextView textView1;
    LinearLayout rlmain;
    LinearLayout ll1;
    LinearLayout.LayoutParams llp;
    Button bn_sceglifile,bn_creafile,bn_salvafile,bn_leggifile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 29) {
             aggiungiView();
            String androidcambia="dalla versione 29 e' possibile scrivere o leggere da un file," +
                    "con alcuni accorgimenti per la versione 29 dobbiamo inserire nel file manifest la seguente riga:" +
                    " android:requestLegacyExternalStorage=\"true\" per la versione 30 e superiore dobbiamo avvalerci di un intent " +
                    "particolare .Comunque google ci mette a disposizione anche degli intent standard" +
                    " del sistema android"+
                    "Vedi:https://stackoverflow.com/questions/62782648/android-11-scoped-storage-permissions";
            textView.setText(" " + "\n"+androidcambia);
            textView1.setText("seconda riga di testo");
            if(Utility.leggichiavefile(this,"SDK11 E SUPERIORE","PERMESSI_SCRITTURA",false)){
                Utility.scrivilog(SDK_INT, "Scrivo senza chiedere più permessi");
            }else {
                requestPermission();
            }
            ReadExternalStorage();
            //chooseFile();
        }else {

            textView = new TextView(this);
            textView.setText("Seconda riga di testo");
            setContentView(textView);


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_READ_EXTERNAL_STORAGE);

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_EXTERNAL_STORAGE);

            } else {
                ReadExternalStorage();
            }

        }
    }

    private void aggiungiView() {
        rlmain = new LinearLayout(this);
       llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        ll1 = new LinearLayout(this);
        ll1.setOrientation(LinearLayout.VERTICAL);
        textView = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(lp);
        ll1.addView(textView);
         textView1 = new TextView(this);
       // LinearLayout .LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView1.setLayoutParams(lp);
        ll1.addView(textView1);


        bn_creafile=new Button(this);
        // LinearLayout .LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        bn_creafile.setLayoutParams(lp);
        bn_creafile.setText("crea file");
        bn_creafile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFile("text/plain","Test.txt");
            }
        });
        ll1.addView(bn_creafile);
        bn_leggifile=new Button(this);
       // LinearLayout .LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        bn_leggifile.setLayoutParams(lp);
        bn_leggifile.setText("leggi file");
        bn_leggifile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
        ll1.addView(bn_leggifile);

        bn_salvafile=new Button(this);
        bn_salvafile.setLayoutParams(lp);
        bn_salvafile.setText("Condividi file");
        bn_salvafile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFile(Uri.parse(textView.getText().toString()),"scrittura file");
            }
        });
        ll1.addView(bn_salvafile);
        rlmain.addView(ll1);
        setContentView(rlmain, llp);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE :
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];

                    if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE) || permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            ReadExternalStorage();
                        } else {
                            Toast.makeText(ExternalStorageTest.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void writeTextFile(File file, String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(text);
        writer.close();
    }

    private String readTextFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder text = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            text.append(line);
            text.append("\n");
        }
        reader.close();
        return text.toString();
    }
    public void ReadExternalStorage() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            textView.setText("No external storage mounted");
        } else {
            File externalDir = Environment.getExternalStorageDirectory();
            File textFile = new File(externalDir.getAbsolutePath()
                    + File.separator + "text.txt");
            try {
                writeTextFile(textFile, "This is a test. Roger");
                String text = readTextFile(textFile);
                textView.setText(text);
                if (!textFile.delete()) {
                    textView.setText("Couldn't remove temporary file");
                }
            } catch (IOException e) {

                textView.setText("Something went wrong! " + e.getMessage()+"\n");

            }
        }
    }

    private void chooseFile(){
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
            intent.setType("text/plain");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            Intent chooserIntent = Intent.createChooser(intent, "Open file");
            startActivityForResult(chooserIntent, 1);

        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("file/*");
            intent.setType("text/plain");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            Intent chooserIntent = Intent.createChooser(intent, "Open file");
            startActivityForResult(chooserIntent, 1);

        }



    }
    private void createFile(String mimeType, String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

        // Filter to only show results that can be "opened", such as
        // a file (as opposed to a list of contacts or timezones).
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Create a file with the requested MIME type.
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        intent.putExtra(Intent.EXTRA_TEXT, "contenuto del file creato");
        startActivityForResult(intent,2);
    }
    public void shareFile(Uri uri, String shareDatei){
        //File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + "abc.txt");
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        // Uri uri = Uri.fromFile(file);
       /* 2. Binary
        shareIntent.setType("image/jpeg");
        3. Multiple content items
        shareIntent.setType("image/*");*/
        shareIntent.setType("text/plain");
       //  shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Test.txt");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareDatei);
        startActivityForResult(Intent.createChooser(shareIntent, "Test.txt"),45);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {


            // Toast.makeText(this, "File Clicked: ", Toast.LENGTH_SHORT).show();
            // Uri uri = data.getData();
            // File myFile = new File(uri.getPath());
            //String filePath = myFile.getAbsolutePath();
            // now read file store at 'filePath' from local storage (this part is fine)
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                final int takeFlags = data.getFlags()
                        & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        this.getContentResolver().takePersistableUriPermission(uri, takeFlags);
                    }
                }
                catch (SecurityException e){
                    e.printStackTrace();
                }
                Log.i("URI", "Uri: " + uri.getPath());
                textView1.setText("PErcorso:"+uri.getPath());

                try {

                   textView.setText("Contenuto:"+readTextFromUri( uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }else {
                Toast.makeText(this, "Nessun file disponibile!: ", Toast.LENGTH_SHORT).show();
            }



        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i("URI", "UriResult: " + uri.getPath());
                textView1.setText(uri.getPath());

                try {
                    OutputStream outputStream =getContentResolver().openOutputStream(uri);
                    outputStream.write(("ho creato il file e ci ho scritto dentro").getBytes());
                    outputStream.close();
                    textView.setText(readTextFromUri( uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }}
        if (requestCode == 45 && resultCode == RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i("URI", "UriResult: " + uri.getPath());
               textView1.setText(uri.getPath());

                try {

                    textView.setText(readTextFromUri( uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }}
        // per sdk>= 30
        if (requestCode == 2237) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {

                    Utility.scrivilog(SDK_INT, "Versione sdk ha ottenuto i permessi di scrittura");
                    Utility.Scrivichiavefile(this,"SDK11 E SUPERIORE","PERMESSI_SCRITTURA",true);
                    Log.d("MOM","perform action");
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
        private String readTextFromUri(Uri uri) throws IOException {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(uri, "r");
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
            parcelFileDescriptor.close();
            return stringBuilder.toString();
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
            ActivityCompat.requestPermissions(ExternalStorageTest.this, new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE );
            ActivityCompat.requestPermissions(ExternalStorageTest.this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE );
        }
    }



}
