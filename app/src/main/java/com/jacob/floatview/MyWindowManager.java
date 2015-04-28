package com.jacob.floatview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import com.jacob.floatview.view.FloatBigView;
import com.jacob.floatview.view.FloatSmallView;

/**
 * Created by jacob-wj on 2015/4/28.
 */
public class MyWindowManager {

    private static MyWindowManager windowManager = new MyWindowManager();
    private MyWindowManager(){}

    public static MyWindowManager newInstance(){
        return windowManager;
    }

    private  WindowManager mWindowManager;

    private  WindowManager.LayoutParams mBigParames;

    private  WindowManager.LayoutParams mSmallParames;

    private FloatSmallView mSmallView;

    private FloatBigView mBigView;

    private boolean isSmallShow = false;

    private boolean isBigShow= false;


    /**
     * 移除屏幕的大的悬浮框
     */
    public  void removeBigView(Context context){
        WindowManager windowManager = getWindowManager(context);
        if (mBigView != null){
            windowManager.removeView(mBigView);
            mBigView = null;
        }
    }

    /**
     * 移除屏幕的小的悬浮框
     */
    public  void removeSmallView(Context context){
        WindowManager windowManager = getWindowManager(context);
        if (mSmallView != null){
            windowManager.removeView(mSmallView);
            mSmallView = null;
        }
    }

    /**
     * 显示大的悬浮框，显示在屏幕的正中心
     */
    public  void showBigView(Context context){
        WindowManager windowManager = getWindowManager(context);
        if (mBigView== null){
            mBigView = new FloatBigView(context);
        }

        if (mBigParames == null){
            int[] screenSize = getScreenSize(context);
            mBigParames = new WindowManager.LayoutParams();
            mBigParames.width = mBigView.width;
            mBigParames.height =mBigView.height;
            mBigParames.x = (screenSize[0]-mBigView.width)/2;
            mBigParames.y = (screenSize[1]-mBigView.height)/2;

            mBigParames.gravity = Gravity.LEFT| Gravity.TOP;
            mBigParames.format = PixelFormat.TRANSPARENT;
            mBigParames.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        windowManager.addView(mBigView,mBigParames);
    }

    /**
     * 创建小的悬浮框，默认显示在屏幕的最右边中心的位置
     */
    public  void showSmallView(Context context){
        WindowManager windowManager = getWindowManager(context);

        if (mSmallView == null){
            mSmallView = new FloatSmallView(context);
        }

        if (mSmallParames == null){
            int[] screenSize = getScreenSize(context);
            mSmallParames = new WindowManager.LayoutParams();
            mSmallParames.width = mSmallView.width;
            mSmallParames.height = mSmallView.height;
            mSmallParames.x = screenSize[0]-mSmallView.width;
            mSmallParames.y = screenSize[1]/2;
            mSmallParames.format = PixelFormat.TRANSPARENT;
            mSmallParames.windowAnimations = 0;
            mSmallParames.gravity = Gravity.LEFT| Gravity.TOP;
            mSmallParames.type = WindowManager.LayoutParams.TYPE_PHONE;
            mSmallParames.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }
        mSmallView.setLayoutParams(mSmallParames);
        windowManager.addView(mSmallView,mSmallParames);
    }


    private  int[] getScreenSize(Context context){
        WindowManager windowManager = getWindowManager(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int[] size = new int[2];
        size[0] = displayMetrics.widthPixels;
        size[1] = displayMetrics.heightPixels;
        return size;
    }


    private   WindowManager getWindowManager(Context context){
        if (mWindowManager == null){
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }
}
