package com.maxll.opengldemos;

import android.os.Bundle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by maxll on 16-12-20.
 */

public class Draw20Mash extends BaseActivity {

    static final float X = 0.5257f;
    static final float Z = 0.8506f;

    float[] verticesData = new float[]{
            -X,0.0f,Z,
            X,0.0f,Z,
            -X,0.0f,-Z,
            X,0.0f,-Z,

            0.0f,Z ,X,
            0.0f,Z,-X,
            0.0f,-Z,X,
            0.0f,-Z,-X,

            Z,X,0.0f,
            -Z,X,0.0f,
            Z,-X,0.0f,
            -Z,-X,0.0f,
    };

    short[] indicesData = new short[]{
            0,4,1, 0,9,4, 9,5,4, 4,5,8, 4,8,1,
            8,10,1, 8,3,10, 5,3,8, 5,2,3, 2,7,3,
            7,10,3, 7,6,10, 7,11,6, 11,0,6, 0,1,6,
            6,1,10, 9,0,11, 9,11,2, 9,2,5, 7,2,11
    };

    float[] colors = {
            0f, 0f, 0f, 1f,
            0f, 0f, 1f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 1f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 1f, 1f,
            1f, 1f, 0f, 1f,
            1f, 1f, 1f, 1f,
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f
    };

    private FloatBuffer vertices;
    private FloatBuffer colorices;
    private ShortBuffer indices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vertices = ByteBuffer.allocateDirect(verticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertices.put(verticesData).position(0);

        colorices = ByteBuffer.allocateDirect(colors.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorices.put(colors).position(0);

        indices = ByteBuffer.allocateDirect(indicesData.length * 2)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        indices.put(indicesData).position(0);
    }
    int angle;

    @Override
    public void DrawScene(GL10 gl) {
        super.DrawScene(gl);

        gl.glColor4f(1.0f,0.0f,1.0f,1.0f);
        gl.glLoadIdentity();
        gl.glTranslatef(0,0,-4);

        gl.glRotatef(angle,0,1,0);

        gl.glFrontFace(GL10.GL_CCW);

        gl.glEnable(GL10.GL_CULL_FACE);

        gl.glCullFace(GL10.GL_BACK);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertices);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4,GL10.GL_FLOAT,0,colorices);
        gl.glDrawElements(GL10.GL_TRIANGLES,indicesData.length,GL10.GL_UNSIGNED_SHORT,indices);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);

        angle++;

        try {
            Thread.currentThread().sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}























