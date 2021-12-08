package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FontTest extends AppCompatActivity {
TextView textView;
    public static final Typeface VAGRoundedBlack(Context context)
    {
        Typeface typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/vag-rounded-black.ttf");
        return typeface;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        textView.setTypeface(VAGRoundedBlack(this));
        textView.setText("Riga di testo con carattere diverso");
        setContentView(textView);
    }
}