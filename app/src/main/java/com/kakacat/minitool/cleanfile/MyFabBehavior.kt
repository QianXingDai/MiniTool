package com.kakacat.minitool.cleanfile

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton

open class MyFabBehavior(context: Context?, attrs: AttributeSet?) : FloatingActionButton.Behavior(context, attrs) {
    private var width = 0
    private var currentState = STATE_SCROLLED_START
    private val additionalHiddenOffsetX = 0
    private var currentAnimator: ViewPropertyAnimator? = null

    override fun onLayoutChild(parent: CoordinatorLayout, child: FloatingActionButton, layoutDirection: Int): Boolean {
        val paramsCompat = child.layoutParams as MarginLayoutParams
        width = child.measuredWidth + paramsCompat.rightMargin
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray) {
        if (dyConsumed > 0) {
            slideEnd(child)
        } else if (dyConsumed < 0) {
            slideUp(child)
        }
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
    }

    private fun slideUp(child: View) {
        if (currentState == STATE_SCROLLED_START) {
            return
        }
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
            child.clearAnimation()
        }
        currentState = STATE_SCROLLED_START
        animateChildTo(child, 0, ENTER_ANIMATION_DURATION.toLong(), AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
    }

    private fun slideEnd(child: View) {
        if (currentState == STATE_SCROLLED_END) {
            return
        }
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
            child.clearAnimation()
        }
        currentState = STATE_SCROLLED_END
        animateChildTo(child, width + additionalHiddenOffsetX, EXIT_ANIMATION_DURATION.toLong(), AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR)
    }

    private fun animateChildTo(child: View, targetX: Int, duration: Long, interpolator: TimeInterpolator) {
        currentAnimator = child.animate()
                .translationX(targetX.toFloat())
                .setInterpolator(interpolator)
                .setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        currentAnimator = null
                    }
                })
    }

    companion object {
        protected const val ENTER_ANIMATION_DURATION = 225
        protected const val EXIT_ANIMATION_DURATION = 175
        private const val STATE_SCROLLED_END = 1
        private const val STATE_SCROLLED_START = 2
    }
}