package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.impl;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class GLGraphicsGL10 {
    GLSurfaceView glViewGL10;
    GL10 glGL10;

    GLGraphicsGL10(GLSurfaceView glView) {
        this.glViewGL10 = glView;
    }

    public GL10 getGL() {
        return glGL10;
    }

    void setGL(GL10 gl) {
        this.glGL10 = gl;
    }

    public int getWidth() {
        return glViewGL10.getWidth();
    }

    public int getHeight() {
        return glViewGL10.getHeight();
    }
}
