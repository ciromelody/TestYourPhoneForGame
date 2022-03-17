package org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics;

import android.opengl.GLES20;

import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Game;
import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.framework.Screen;
import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.impl.GLGameGL10;
import org.altervista.ultimaprovaprimadi.ciromelody.testyourphoneforgame.glbasics.impl.GLGraphicsGL10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class GLPrimoTriangolo extends GLGameGL10 {
    public Screen getStartScreen() {
        return new PrimoTriangolo(this);
    }

    class PrimoTriangolo extends Screen {
        GLGraphicsGL10 glGraphicsGL10;
        FloatBuffer vertices;
       //float triangleCoords[] ={0.0f,0.0f,319.0f,0.0f,160.0f,419.0f};
       int x;
        int y;


        public PrimoTriangolo(Game game) {
            super(game);
            glGraphicsGL10 = ((GLGameGL10) game).getGLGraphicsGL10();
            glGraphicsGL10 = ((GLGameGL10) game).getGLGraphicsGL10();
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * 2 * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            vertices = byteBuffer.asFloatBuffer();
            vertices.put( new float[] {    0.0f,   0.0f,
                    319.0f,   0.0f,
                    160.0f, 479.0f});
            vertices.flip();





        }

        @Override
        public void update(float deltaTime) {
            game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();

        }

        @Override
        public void present(float[] matrix) {
            GL10 gl= glGraphicsGL10.getGL();



            //gl.glViewport(0, 0, glGraphicsGL10.getWidth(), glGraphicsGL10.getHeight());

            gl.glViewport(0, 0, x++, y++);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, 320, 0, 480, 1, -1);
            gl.glColor4f(1, 0, 0, 1);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glVertexPointer( 2, GL10.GL_FLOAT, 0, vertices);
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);



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