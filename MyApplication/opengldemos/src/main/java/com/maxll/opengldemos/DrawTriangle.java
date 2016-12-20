package com.maxll.opengldemos;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by maxll on 16-12-20.
 */

public class DrawTriangle extends BaseActivity {

    float[] verticesData = {
            -0.8f,-0.4f * 1.732f,0.0f,
            0.0f,-0.4f * 1.732f,0.0f,
            -0.4f,0.4f * 1.732f,0.0f,


            0.0f,-0.0f * 1.732f,0.0f,
            0.8f,-0.0f * 1.732f,0.0f,
            0.4f,0.4f * 1.732f,0.0f
    };
    int index = 0;

    @Override
    public void DrawScene(GL10 gl) {
        super.DrawScene(gl);

        FloatBuffer vertices = ByteBuffer.allocateDirect(verticesData.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertices.put(verticesData).position(0);

        gl.glLoadIdentity();
        gl.glTranslatef(0,0,-4);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertices);
        index++;
        index %= 10;
        switch (index){
            case 0:
            case 1:
            case 2:
                gl.glColor4f(1.0f,0.0f,0.0f,1.0f);
                gl.glDrawArrays(GL10.GL_TRIANGLES,0,6);
                break;
            case 3:
            case 4:
            case 5:
                gl.glColor4f(0.0f,1.0f,0.0f,1.0f);
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,6);
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                gl.glColor4f(0.0f,0.0f,1.0f,1.0f);
                gl.glDrawArrays(GL10.GL_TRIANGLE_FAN,0,6);
                break;
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        try {
            Thread.currentThread().sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
