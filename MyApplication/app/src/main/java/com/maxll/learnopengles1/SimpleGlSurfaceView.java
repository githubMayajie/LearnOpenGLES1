package com.maxll.learnopengles1;

import android.content.Context;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by maxll on 16-12-20.
 */

public class SimpleGlSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {


    private SurfaceHolder surfaceHolder;
    private Thread thread;
    private boolean running;

    private Renderer renderer;

    private int width,height;

    public SimpleGlSurfaceView(Context context) {
        super(context);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        synchronized (this){
            this.width = width;
            this.height = height;

            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running = false;
        try {
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        thread = null;
    }


    //Renderer
    public interface Renderer{
        void EGLCreate(SurfaceHolder surfaceHolder);
        void EGLDestroy();
        int Initialize(int width,int height);
        void DrawScene(int width,int height);
    }

    //runnable
    @Override
    public void run() {
        synchronized (this){
            renderer.EGLCreate(surfaceHolder);

            renderer.Initialize(this.width,this.height);

            running = true;
            while (running){
                renderer.DrawScene(this.width,this.height);
            }
            renderer.EGLDestroy();
        }
    }





    public class GLRenderer implements Renderer{

        private EGL10 egl;
        private GL10 gl;
        private EGLDisplay eglDisplay;
        private EGLConfig eglConfig;
        private EGLContext eglContext;
        private EGLSurface eglSurface;

        @Override
        public void EGLCreate(SurfaceHolder surfaceHolder) {
            int num_config[] = new int[1];
            EGLConfig[] configs = new EGLConfig[1];
            int[] configSpec = {
                    EGL10.EGL_RED_SIZE,8,
                    EGL10.EGL_GREEN_SIZE,8,
                    EGL10.EGL_BLUE_SIZE,8,

                    EGL10.EGL_SURFACE_TYPE,EGL10.EGL_WINDOW_BIT,
                    EGL10.EGL_NONE
            };
            egl = (EGL10) EGLContext.getEGL();
            eglDisplay = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            egl.eglInitialize(eglDisplay,null);
            egl.eglChooseConfig(eglDisplay,configSpec,configs,1,num_config);

            eglConfig = configs[0];
            eglContext = egl.eglCreateContext(eglDisplay,eglConfig,EGL10.EGL_NO_CONTEXT,null);

            eglSurface = egl.eglCreateWindowSurface(eglDisplay,eglConfig,surfaceHolder,null);
            egl.eglMakeCurrent(eglDisplay,eglSurface,eglSurface,eglContext);

            gl = (GL10) eglContext.getGL();
        }

        @Override
        public void EGLDestroy() {
            if(eglSurface != null){
                egl.eglMakeCurrent(eglDisplay,EGL10.EGL_NO_SURFACE,
                        EGL10.EGL_NO_SURFACE,EGL10.EGL_NO_CONTEXT);
                egl.eglDestroySurface(eglDisplay,eglSurface);
                eglSurface = null;
            }
            if(eglContext != null){
                egl.eglDestroyContext(eglDisplay,eglContext);
                eglContext = null;
            }
            if(eglDisplay != null){
                egl.eglTerminate(eglDisplay);
                eglDisplay = null;
            }
        }

        @Override
        public int Initialize(int width, int height) {
            gl.glClearColor(1.0f,1.0f,1.0f,0.0f);
            return 1;
        }

        @Override
        public void DrawScene(int width, int height) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

            egl.eglSwapBuffers(eglDisplay,eglSurface);
        }
    }
}
