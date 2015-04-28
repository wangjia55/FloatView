package com.jacob.floatview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import com.jacob.floatview.view.FloatBigView;
import com.jacob.floatview.view.FloatSmallView;

/**
 * windowManager 的工具类，统一对view 的显示和消息进行管理
 */
public class MyWindowManager {

    private static MyWindowManager windowManager = new MyWindowManager();

    private MyWindowManager(){}

    public static MyWindowManager newInstance(){
        return windowManager;
    }

    private  WindowManager mWindowManager;

    private  WindowManager.LayoutParams mBigParams;

    private  WindowManager.LayoutParams mSmallParams;

    private FloatSmallView mSmallView;

    private FloatBigView mBigView;

    /**
     * 大的悬浮框是否显示
     */
    public boolean isSmallShow = false;

    /**
     * 小的悬浮框是否显示
     */
    public boolean isBigShow= false;


    /**
     * 移除屏幕的大的悬浮框
     */
    public  void removeBigView(Context context){
        WindowManager windowManager = getWindowManager(context);
        if (mBigView != null){
            windowManager.removeView(mBigView);
            mBigView = null;
            isBigShow = true;
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
            isSmallShow = false;
        }
    }

    /**
     * 显示大的悬浮框，显示在屏幕的正中心
     */
    public  void showBigView(Context context){
        if (isBigShow) return;
        WindowManager windowManager = getWindowManager(context);
        if (mBigView== null){
            mBigView = new FloatBigView(context);
        }

        if (mBigParams == null){
            int[] screenSize = getScreenSize(context);
            mBigParams = new WindowManager.LayoutParams();
            mBigParams.width = mBigView.width;
            mBigParams.height =mBigView.height;
            mBigParams.x = (screenSize[0]-mBigView.width)/2;
            mBigParams.y = (screenSize[1]-mBigView.height)/2;

            mBigParams.gravity = Gravity.LEFT| Gravity.TOP;
            mBigParams.format = PixelFormat.TRANSPARENT;
            mBigParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        windowManager.addView(mBigView, mBigParams);
        isBigShow = true;
    }

    /**
     * 创建小的悬浮框，默认显示在屏幕的最右边中心的位置
     */
    public  void showSmallView(Context context){
        if (isSmallShow) return;
        WindowManager windowManager = getWindowManager(context);

        if (mSmallView == null){
            mSmallView = new FloatSmallView(context);
        }

        if (mSmallParams == null){
            int[] screenSize = getScreenSize(context);
            mSmallParams = new WindowManager.LayoutParams();
            mSmallParams.width = mSmallView.width;
            mSmallParams.height = mSmallView.height;
            mSmallParams.x = screenSize[0]-mSmallView.width;
            mSmallParams.y = screenSize[1]/2;
            mSmallParams.format = PixelFormat.TRANSPARENT;
            mSmallParams.windowAnimations = 0;
            mSmallParams.gravity = Gravity.LEFT| Gravity.TOP;
            mSmallParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            mSmallParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }
        mSmallView.setLayoutParams(mSmallParams);
        windowManager.addView(mSmallView, mSmallParams);
        isSmallShow = true;
    }


    /**
     * 获取屏幕的尺寸
     */
    public int[] getScreenSize(Context context){
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
