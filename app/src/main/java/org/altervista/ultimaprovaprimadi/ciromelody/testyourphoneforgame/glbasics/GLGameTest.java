package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics;

import java.util.Random;

import android.opengl.GLES20;
import  org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Game;
import  org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Screen;
import  org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.impl.GLGame;
import  org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.impl.GLGraphics;

public class GLGameTest extends GLGame {
    public Screen getStartScreen() {
        return new TestScreen(this);
    }

    class TestScreen extends Screen {
        GLGraphics glGraphics;
        Random rand = new Random();

        public TestScreen(Game game) {
            super(game);
            glGraphics = ((GLGame) game).getGLGraphics();
        }

        @Override
        public void present(float[] matrix) {
            GLES20 gl =  glGraphics.getGL();
            //GLES20.glViewport(0,0,34,67);

            glGraphics.clearScreen(rand.nextFloat()
                    , rand.nextFloat(), rand.nextFloat(), 1);
        }

        @Override
        public void update(float deltaTime) {
        }

        @Override
        public void pause() {
        }

        @Override
        public void resume() {
        }

        @Override
        public void dispose() {
        }
    }
}

