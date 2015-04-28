package com.jacob.floatview;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by jacob-wj on 2015/4/27.
 */
public class FloatService extends Service {

    private MyWindowManager mMyManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG","onCreate");
        mMyManager = MyWindowManager.newInstance();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 启动service的同时也显示view
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG","onStartCommand");
        mMyManager.removeBigView(getApplicationContext());
        mMyManager.showSmallView(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","onDestroy");
        if (mMyManager != null){
            mMyManager.removeBigView(getApplicationContext());
            mMyManager.removeSmallView(getApplicationContext());
        }
    }
}
