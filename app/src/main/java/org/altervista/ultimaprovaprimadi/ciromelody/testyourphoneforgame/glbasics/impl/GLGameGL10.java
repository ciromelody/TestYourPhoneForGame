package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.impl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Audio;
import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.FileIO;
import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Game;
import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Graphics;
import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Input;
import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Screen;
import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.implement.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.os.PowerManager.*;


public abstract class GLGameGL10 extends Activity implements Game, Renderer, GLSurfaceView.Renderer {
        enum GLGameState {
            Initialized,
            Running,
            Paused,
            Finished,
            Idle
        }

        GLSurfaceView glView;
        GLGraphicsGL10 glGraphics;
        Audio audio;
        Input input;
        FileIO fileIO;
        Screen screen;
        GLGameState state = GLGameState.Initialized;
        Object stateChanged = new Object();
        long startTime = System.nanoTime();
        PowerManager.WakeLock wakeLock;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            glView = new GLSurfaceView(this);
            glView.setRenderer(this);
            setContentView(glView);

            glGraphics = new GLGraphicsGL10(glView);
            fileIO = new AndroidFileIO(this );
            audio = new AndroidAudio(this);
            input = new AndroidInput(this, glView, 1, 1);
            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            //wakeLock = powerManager.newWakeLock(FULL_WAKE_LOCK, "GLGameGL10");
        }

        public void onResume() {
            super.onResume();
            glView.onResume();
           //wakeLock.acquire();
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            glGraphics.setGL(gl);

            synchronized(stateChanged) {
                if(state == GLGameState.Initialized)
                    screen = getStartScreen();
                state = GLGameState.Running;
                screen.resume();
                startTime = System.nanoTime();
            }
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLGameState state = null;

            synchronized(stateChanged) {
                state = this.state;
            }

            if(state == GLGameState.Running) {
                float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
                float[] deltaTime1 = {(System.nanoTime()-startTime) / 1000000000.0f};
                startTime = System.nanoTime();

                screen.update(deltaTime);
                screen.present(deltaTime1);
            }

            if(state == GLGameState.Paused) {
                screen.pause();
                synchronized(stateChanged) {
                    this.state = GLGameState.Idle;
                    stateChanged.notifyAll();
                }
            }

            if(state == GLGameState.Finished) {
                screen.pause();
                screen.dispose();
                synchronized(stateChanged) {
                    this.state = GLGameState.Idle;
                    stateChanged.notifyAll();
                }
            }
        }

        @Override
        public void onPause() {
            synchronized(stateChanged) {
                if(isFinishing())
                    state = GLGameState.Finished;
                else
                    state = GLGameState.Paused;
                while(true) {
                    try {
                        stateChanged.wait();
                        break;
                    } catch(InterruptedException e) {
                    }
                }
            }
            //wakeLock.release();
            glView.onPause();
            super.onPause();
        }

        public GLGraphicsGL10 getGLGraphicsGL10() {
            return glGraphics;
        }

        @Override
        public Input getInput() {
            return input;
        }

        @Override
        public FileIO getFileIO() {
            return fileIO;
        }

        @Override
        public Graphics getGraphics() {
            throw new IllegalStateException("We are using OpenGL!");
        }

        @Override
        public Audio getAudio() {
            return audio;
        }

        @Override
        public void setScreen(Screen screen) {
            if (screen == null)
                throw new IllegalArgumentException("Screen must not be null");

            this.screen.pause();
            this.screen.dispose();
            screen.resume();
            screen.update(0);
            this.screen = screen;
        }

        @Override
        public Screen getCurrentScreen() {
            return screen;
        }

        @Override
        public Screen getStartScreen() {
            return null;
        }
    }
