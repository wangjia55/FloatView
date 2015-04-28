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


    public void setMemory(int memory) {
        mTextView.setText("" + memory + " %");
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
                mLastY = y;
                updateViewLocation();
                break;
            case MotionEvent.ACTION_UP:
                long endTime = System.currentTimeMillis();
                if (endTime -startTime <= clickTimeOut){
                    showBigView();
                }
                break;
        }
        return true;
    }

    private void showBigView() {
        if (myWindowManager == null){
            myWindowManager = MyWindowManager.newInstance();
        }
        myWindowManager.removeSmallView(getContext());
        myWindowManager.showBigView(getContext());
    }

    private void updateViewLocation() {
        mLayoutParams.x = (int) mLastX;
        mLayoutParams.y = (int) mLastY;
        mWindowManager.updateViewLayout(this,mLayoutParams);
    }
}
