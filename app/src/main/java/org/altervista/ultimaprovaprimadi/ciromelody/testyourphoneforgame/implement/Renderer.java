package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public interface Renderer {
    public void onSurfaceCreated(GL10 gl, EGLConfig config);
    public void onSurfaceChanged(GL10 gl,int width,int heigth);
    public void onDrawFrame(GL10 gl);
}
