package com.kakacat.minitool.common.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kakacat.minitool.common.util.UiUtil.showLoading

class RecycleViewScrollListener(private val onSwipeUpRefresh: OnSwipeUpRefresh) : RecyclerView.OnScrollListener() {

    private var lastItemPosition = 0
    private var layoutManagerType: Int = -1
    private var lastPositions: IntArray? = null

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        val layoutManager = recyclerView.layoutManager!!
        if (layoutManagerType == -1) {
            when (layoutManager) {
                is LinearLayoutManager -> {
                    LayoutManagerType.LinearLayoutManager
                }
                is GridLayoutManager -> {
                    LayoutManagerType.GridLayoutManager
                }
                is StaggeredGridLayoutManager -> {
                    LayoutManagerType.StaggeredGridLayoutManager
                }
                else -> {
                    throw RuntimeException("UnSupport LayoutManager")
                }
            }
        }
        when (layoutManagerType) {
            LayoutManagerType.LinearLayoutManager -> {
                lastItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
            LayoutManagerType.GridLayoutManager -> {
                lastItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }
            LayoutManagerType.StaggeredGridLayoutManager -> {
                val manager = layoutManager as StaggeredGridLayoutManager
                if (lastPositions == null) {
                    lastPositions = IntArray(manager.spanCount)
                }
                manager.findLastVisibleItemPositions(lastPositions)
                for (lastPosition in lastPositions!!) {
                    lastItemPosition = lastPosition.coerceAtLeast(lastItemPosition)
                }
            }
            else -> {
            }
        }
        if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.childCount > 0 && lastItemPosition >= layoutManager.itemCount - 1) {
            showLoading(recyclerView)
            onSwipeUpRefresh.loadMore()
        }
    }

    private fun showLoading(recyclerView: RecyclerView) {
        showLoading(recyclerView.context, recyclerView)
    }

    interface OnSwipeUpRefresh {
        fun loadMore()
    }

    private interface LayoutManagerType {
        companion object {
            const val LinearLayoutManager = 0
            const val GridLayoutManager = 1
            const val StaggeredGridLayoutManager = 2
        }
    }
}