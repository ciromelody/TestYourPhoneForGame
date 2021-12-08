package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement.MyPhoneHard;

public class AndroidMyPhoneHardTest extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView=new TextView(this);
        setContentView(textView);
        MyPhoneHard hardwaretelefono= new MyPhoneHard();
       textView.setText(hardwaretelefono.getDetail());
    }
}