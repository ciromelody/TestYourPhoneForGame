package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement.AndroidMusic;
import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement.Music;

import java.io.IOException;

public class MediaPlayerTest extends AppCompatActivity {
    AssetFileDescriptor descriptor;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView=new TextView(this);
        setContentView(textView);
        try {
            AssetManager assetManager = this.getAssets();
           descriptor = assetManager.openFd("explosion-02.ogg");

        } catch (IOException e) {
            textView.setText("Couldn't load mediaplayer effect from asset, "
                    + e.getMessage());
        }
        Music brano_musicale= new AndroidMusic(descriptor);
        brano_musicale.play();
        textView.setText("Eseguito il file explosion-02.ogg");
    }
}