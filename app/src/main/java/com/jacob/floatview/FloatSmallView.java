package com.jacob.floatview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by jacob-wj on 2015/4/28.
 */
public class FloatSmallView extends LinearLayout {

    private TextView mTextView;

    public FloatSmallView(Context context) {
        this(context, null);
    }

    public FloatSmallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatSmallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_small_window, this);
        mTextView = (TextView) findViewById(R.id.text_view_memory);
    }


    public void setMemory(int memory){
        mTextView.setText(""+memory+" %");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
