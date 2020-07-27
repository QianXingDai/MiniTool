package com.kakacat.minitool.cleanfile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyFabBehavior extends FloatingActionButton.Behavior {
    protected static final int ENTER_ANIMATION_DURATION = 225;
    protected static final int EXIT_ANIMATION_DURATION = 175;

    private static final int STATE_SCROLLED_END = 1;
    private static final int STATE_SCROLLED_START = 2;

    private int width = 0;
    private int currentState = STATE_SCROLLED_START;
    private int additionalHiddenOffsetX = 0;
    @Nullable
    private ViewPropertyAnimator currentAnimator;

    public MyFabBehavior() {
    }

    public MyFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull FloatingActionButton child, int layoutDirection) {
        ViewGroup.MarginLayoutParams paramsCompat = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        width = child.getMeasuredWidth() + paramsCompat.rightMargin;
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        if (dyConsumed > 0) {
            slideEnd(child);
        } else if (dyConsumed < 0) {
            slideUp(child);
        }
        super.onNestedScroll(coordinatorLayout,child,target,dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,type,consumed);
    }

    private void slideUp(@NonNull View child) {
        if (currentState == STATE_SCROLLED_START) {
            return;
        }

        if (currentAnimator != null) {
            currentAnimator.cancel();
            child.clearAnimation();
        }
        currentState = STATE_SCROLLED_START;
        animateChildTo(child, 0, ENTER_ANIMATION_DURATION, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
    }

    private void slideEnd(View child) {
        if (currentState == STATE_SCROLLED_END) {
            return;
        }

        if (currentAnimator != null) {
            currentAnimator.cancel();
            child.clearAnimation();
        }
        currentState = STATE_SCROLLED_END;
        animateChildTo(child, width + additionalHiddenOffsetX, EXIT_ANIMATION_DURATION, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
    }

    private void animateChildTo(@NonNull View child, int targetX, long duration, TimeInterpolator interpolator) {
        currentAnimator = child.animate()
                .translationX(targetX)
                .setInterpolator(interpolator)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        currentAnimator = null;
                    }
                });
    }
}
