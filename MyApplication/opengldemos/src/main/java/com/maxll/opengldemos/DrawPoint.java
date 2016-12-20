package com.maxll.opengldemos;

import android.opengl.GLES10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by maxll on 16-12-20.
 */

public class DrawPoint extends BaseActivity {

    float[] verticesData = {
            -0.8f,-0.4f * 1.732f,0.0f,
            0.8f,-0.4f * 1.732f,0.0f,
            0.0f,0.4f * 1.732f,0.0f
    };


    @Override
    public void DrawScene(GL10 gl) {
        super.DrawScene(gl);

        FloatBuffer vertices = ByteBuffer.allocateDirect(verticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertices.put(verticesData).position(0);

        gl.glColor4f(1.0f,0.0f,0.0f,1.0f);
        gl.glPointSize(80f);
        gl.glLoadIdentity();

        gl.glTranslatef(0,0,-4);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertices);
        gl.glDrawArrays(GLES10.GL_POINTS,0,3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
