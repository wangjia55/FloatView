package com.jacob.floatview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jacob.floatview.MyWindowManager;
import com.jacob.floatview.R;

/**
 * Created by jacob-wj on 2015/4/28.
 */
public class FloatBigView extends LinearLayout implements View.OnClickListener {

    private MyWindowManager myWindowManager;

    public int width;

    public int height;

    public FloatBigView(Context context) {
        this(context, null);
    }

    public FloatBigView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatBigView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_big_window, this);
        findViewById(R.id.button_back).setOnClickListener(this);

        View container = findViewById(R.id.container);
        width = container.getLayoutParams().width;
        height = container.getLayoutParams().height;
    }

    @Override
    public void onClick(View v) {
        if (myWindowManager == null) {
            myWindowManager = MyWindowManager.newInstance();
        }
        myWindowManager.removeBigView(getContext());
        myWindowManager.showSmallView(getContext());
    }
}
