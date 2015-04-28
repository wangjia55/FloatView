package com.jacob.floatview;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by jacob-wj on 2015/4/27.
 */
public class FloatService extends Service {

    private View mPopupView;
    private WindowManager mWindowManger;
    private WindowManager.LayoutParams mLayoutParames;


    @Override
    public void onCreate() {
        super.onCreate();
        mWindowManger = (WindowManager) getSystemService(WINDOW_SERVICE);
        mPopupView  = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_big_window,null);
    }


    private void initLayoutParams(){
        mLayoutParames = new WindowManager.LayoutParams();
        mLayoutParames.width = 0;
        mLayoutParames.height = 0;
        mLayoutParames.windowAnimations = 0;
        mLayoutParames.format = PixelFormat.TRANSPARENT;
        mLayoutParames.flags  = 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWindowManger != null){

        }
    }
}
