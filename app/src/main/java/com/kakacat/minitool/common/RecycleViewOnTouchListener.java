package com.kakacat.minitool.common;

import android.view.MotionEvent;
import android.view.View;

public interface RecycleViewOnTouchListener {

    boolean onTouch(View v, MotionEvent event);
}
