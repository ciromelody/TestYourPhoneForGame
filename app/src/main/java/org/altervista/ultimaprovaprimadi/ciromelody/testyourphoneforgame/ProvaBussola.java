package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement.CompassHolder;

public class ProvaBussola extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);
        CompassHolder compassHolder=new CompassHolder(this,textView);

    }
}