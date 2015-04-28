package com.jacob.floatview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jacob.floatview.MyWindowManager;
import com.jacob.floatview.R;

import java.lang.reflect.Field;

/**
 * Created by jacob-wj on 2015/4/28.
 */
public class FloatSmallView extends LinearLayout {

    private TextView mTextView;

    public int width;

    public int height;

    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mLayoutParams;

    private long startTime;

    private long clickTimeOut;

    private float mLastX;

    private float mLastY;

    private MyWindowManager myWindowManager;

    private int statusBarHeight;

    public FloatSmallView(Context context) {
        this(context, null);
    }

    public FloatSmallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatSmallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_small_window, this);
        mTextView = (TextView) findViewById(R.id.text_view_memory);

        width = mTextView.getLayoutParams().width;
        height = mTextView.getLayoutParams().height;

        clickTimeOut = ViewConfiguration.getTapTimeout();
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

    }


    public void setLayoutParams(WindowManager.LayoutParams params){
        mLayoutParams = params;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastX = x;
                mLastY = y-getStatusBarHeight();
                updateViewLocation();
                break;
            case MotionEvent.ACTION_UP:
                long endTime = System.currentTimeMillis();
                if (endTime -startTime <= clickTimeOut){
                    showBigView();
                }else{
                    resetViewLocation();
                }
                break;
        }
        return true;
    }

    private void resetViewLocation() {
        if (myWindowManager == null){
            myWindowManager = MyWindowManager.newInstance();
        }
        int[] size = myWindowManager.getScreenSize(getContext());
        if (mLastX>size[0]/2){
            mLayoutParams.x = size[0]-width;
            mLayoutParams.y = (size[1]-height-statusBarHeight)/2;
        }else{
            mLayoutParams.x = 0;
            mLayoutParams.y = (size[1]-height-statusBarHeight)/2;
        }
        mWindowManager.updateViewLayout(this,mLayoutParams);
    }

    /**
     * 如果点击，则显示详细的View
     */
    private void showBigView() {
        if (myWindowManager == null){
            myWindowManager = MyWindowManager.newInstance();
        }
        myWindowManager.isSmallShow = false;
        myWindowManager.isBigShow = false;
        int[] size = myWindowManager.getScreenSize(getContext());
        mLayoutParams.x = size[0]-width;
        mLayoutParams.y = (size[1]-height-statusBarHeight)/2;
        myWindowManager.removeSmallView(getContext());
        myWindowManager.showBigView(getContext());
    }

    private void updateViewLocation() {
        mLayoutParams.x = (int) mLastX;
        mLayoutParams.y = (int) mLastY;
        mWindowManager.updateViewLayout(this,mLayoutParams);
    }

    /**
     * 获取状态栏的高度
     */
    private int getStatusBarHeight(){
       if (statusBarHeight == 0 ){
           try {
               Class<?> c = Class.forName("com.android.internal.R$dimen");
               Object object = c.newInstance();
               Field field = object.getClass().getField("status_bar_height");
               int x = (int) field.get(0);
               statusBarHeight = getResources().getDimensionPixelSize(x);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
        return statusBarHeight;
    }
}
