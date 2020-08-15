package com.kakacat.minitool.todayinhistory

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper

class MyListView(context: Context?, attrs: AttributeSet?) : ListView(context, attrs), NestedScrollingChild {

    private val mScrollingChildHelper by lazy { NestedScrollingChildHelper(this) }

    init {
        isNestedScrollingEnabled = true
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return mScrollingChildHelper.isNestedScrollingEnabled
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mScrollingChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return mScrollingChildHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        mScrollingChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return mScrollingChildHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
                                      dyUnconsumed: Int, offsetInWindow: IntArray?): Boolean {
        return mScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?): Boolean {
        return mScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return mScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }
}