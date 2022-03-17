package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement;

import android.opengl.GLSurfaceView;
import android.util.Log;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SimpleRenderer implements Renderer, GLSurfaceView.Renderer {
Random rand =new Random();
int larghezzaFinestra;
int altezzaFinestra;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d("GLSurfaceTEst","GLViewSurface creata");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int heigth) {
        Log.d("GLSurfaceTEst","Dimensioni finestra cambiate larghezza:"+width+" altezza:"+heigth);
        larghezzaFinestra=width;
        altezzaFinestra=heigth;
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        Log.d("GLSurfaceTEst","disegno qualcosa");
        gl.glClearColor(rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);


    }
}
