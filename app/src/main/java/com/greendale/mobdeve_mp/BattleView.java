package com.greendale.mobdeve_mp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class BattleView extends SurfaceView implements SurfaceHolder.Callback{

        private SurfaceHolder surfaceHolder = null;
        private Context activityContext;
        private Battle activity;

        public BattleView(Context context, Battle activity) {
            super(context);
            this.activity = activity;

            if (context instanceof Battle) {
                activityContext = (Battle) context;
            }

            if(surfaceHolder == null) {
                surfaceHolder = getHolder();
                surfaceHolder.addCallback(this);
            }

            this.activityContext = context;
            this.setBackgroundColor(Color.BLACK);
        }
        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        }
        @Override
        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            // Tap is detected
            if(action == MotionEvent.ACTION_DOWN){
                activity.enemyHP--;
                activity.enemyBar.setProgress((activity.enemyHP)%100);
            }
            return true;
        }

}
