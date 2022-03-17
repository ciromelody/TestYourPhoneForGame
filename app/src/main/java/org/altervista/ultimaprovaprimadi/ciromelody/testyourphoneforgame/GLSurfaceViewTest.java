package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement.SimpleRenderer;

public class GLSurfaceViewTest extends AppCompatActivity {
GLSurfaceView glView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN_);
        glView=new GLSurfaceView(this);
        glView.setRenderer(new SimpleRenderer());
        setContentView(glView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }
}